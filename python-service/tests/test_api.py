from fastapi.testclient import TestClient
from unittest.mock import patch
import pandas as pd

from market_api.app import app

client = TestClient(app)

def _fake_yf_download(symbols, period, auto_adjust, progress):
    """
    Return a deterministic DataFrame that mimics yfinance.download output:
    a DataFrame with MultiIndex columns containing ("Adj Close", <TICKER>).
    """
    dates = pd.date_range("2020-01-01", periods=6, freq="D")

    # Ensure list
    if isinstance(symbols, str):
        syms = [symbols]
    else:
        syms = list(symbols)

    cols = pd.MultiIndex.from_product([["Adj Close"], syms])
    df = pd.DataFrame(index=dates, columns=cols, dtype=float)

    # Create simple price series
    for i, s in enumerate(syms):
        base = 100.0 + i * 10.0
        df[("Adj Close", s)] = [base, base + 1, base + 2, base + 3, base + 4, base + 5]

    return df

@patch("market_api.app.yf.download", side_effect=_fake_yf_download)
def test_estimate_ok_returns_schema(mock_download):
    resp = client.get("/estimate", params={
        "symbols": "VOO,AGG",
        "weights": "0.6,0.4",
        "period": "5y"
    })

    assert resp.status_code == 200
    data = resp.json()

    # basic schema checks
    assert data["period"] == "5y"
    assert data["symbols"] == ["VOO", "AGG"]
    assert len(data["weights"]) == 2
    assert abs(sum(data["weights"]) - 1.0) < 1e-9

    assert "mean" in data
    assert "volatility" in data
    assert isinstance(data["mean"], float)
    assert isinstance(data["volatility"], float)


def test_estimate_invalid_length_mismatch_returns_error_json():
    resp = client.get("/estimate", params={
        "symbols": "VOO,AGG",
        "weights": "1.0",
        "period": "5y"
    })

    assert resp.status_code == 400
    assert resp.json()["detail"]


def test_estimate_invalid_weights_sum_leq_zero_returns_error_json():
    resp = client.get("/estimate", params={
        "symbols": "VOO,AGG",
        "weights": "0,0",
        "period": "5y"
    })

    assert resp.status_code == 400
    assert resp.json()["detail"]



@patch("market_api.app.yf.download", side_effect=_fake_yf_download)
def test_estimate_normalizes_weights(mock_download):
    resp = client.get("/estimate", params={
        "symbols": "VOO,AGG",
        "weights": "60,40",  # should normalize to 0.6 / 0.4
        "period": "10y"
    })

    assert resp.status_code == 200
    data = resp.json()
    assert abs(data["weights"][0] - 0.6) < 1e-9
    assert abs(data["weights"][1] - 0.4) < 1e-9