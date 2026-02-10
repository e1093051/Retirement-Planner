package com.ycfan.retirement_planner.model.dto;

import java.util.List;

public class ReturnVolatilityEstimate {
    private String period;
    private List<String> symbols;
    private List<Double> weights;
    private double mean;
    private double volatility;

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public List<String> getSymbols() { return symbols; }
    public void setSymbols(List<String> symbols) { this.symbols = symbols; }

    public List<Double> getWeights() { return weights; }
    public void setWeights(List<Double> weights) { this.weights = weights; }

    public double getMean() { return mean; }
    public void setMean(double mean) { this.mean = mean; }

    public double getVolatility() { return volatility; }
    public void setVolatility(double volatility) { this.volatility = volatility; }
}
