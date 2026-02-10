"""
Return annual return and volatility of equity/equity combination over a specified period (till now)
"""
from fastapi import FastAPI, Query, HTTPException
import yfinance as yf
import numpy as np
import pandas as pd

app = FastAPI(title="Market Assumptions Service")

def annualize_mu_sigma(daily_returns: pd.Series) -> tuple[float, float]:
    mu_daily = daily_returns.mean()
    sigma_daily = daily_returns.std(ddof=1)
    mu_annual = float(mu_daily * 252.0)
    sigma_annual = float(sigma_daily * np.sqrt(252.0))
    return mu_annual, sigma_annual

@app.get("/estimate")
def estimate(
    symbols: str = Query(..., description="Comma-separated tickers, e.g. VOO,AGG"),
    weights: str = Query(..., description="Comma-separated weights, e.g. 0.6,0.4"),
    period: str = Query("10y", description="yfinance period, e.g. 5y, 10y")
):
    syms = [s.strip().upper() for s in symbols.split(",") if s.strip()]
    w = [float(x.strip()) for x in weights.split(",") if x.strip()]

    if len(syms) == 0 or len(w) == 0 or len(syms) != len(w):
        raise HTTPException(
            status_code=400,
            detail="symbols and weights must be same length and non-empty"
        )

    wsum = sum(w)
    if wsum <= 0:
        raise HTTPException(status_code=400, detail="weights must sum to > 0")
    w = [x / wsum for x in w]  # normalize

    # Download adjusted close prices
    data = yf.download(syms, period=period, auto_adjust=False, progress=False)
    if data is None or len(data) == 0:
            raise HTTPException(status_code=502, detail="no data returned from market data provider")

    # yfinance returns multi-index columns when multiple symbols
    if "Adj Close" in data:
        prices = data["Adj Close"].dropna(how="all")
    else:
        raise HTTPException(status_code=502, detail="unexpected yfinance output; missing Adj Close")

    # Daily returns
    rets = prices.pct_change().dropna()

    # Portfolio daily returns
    if isinstance(rets, pd.Series):
        # single symbol
        port = rets
    else:
        # multiple symbols
        # align columns to syms order
        rets = rets[syms].dropna()
        port = rets.dot(np.array(w))

    mu, sigma = annualize_mu_sigma(port)

    return {
        "period": period,
        "symbols": syms,
        "weights": w,
        "mean": mu,
        "volatility": sigma
    }
