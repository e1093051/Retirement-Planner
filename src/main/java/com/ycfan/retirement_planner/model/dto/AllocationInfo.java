package com.ycfan.retirement_planner.model.dto;

public class AllocationInfo {
    private String symbols;
    private String weights;
    private String period;

    public AllocationInfo() {}

    public AllocationInfo(String symbols, String weights, String period) {
        this.symbols = symbols;
        this.weights = weights;
        this.period = period;
    }

    public String getSymbols() { return symbols; }
    public void setSymbols(String symbols) { this.symbols = symbols; }

    public String getWeights() { return weights; }
    public void setWeights(String weights) { this.weights = weights; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
}
