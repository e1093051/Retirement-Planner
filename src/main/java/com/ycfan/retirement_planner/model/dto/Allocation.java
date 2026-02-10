package com.ycfan.retirement_planner.model.dto;

public class Allocation {
    private final String symbols;
    private final String weights;
    private final String period;

    public Allocation(String symbols, String weights, String period) {
        this.symbols = symbols;
        this.weights = weights;
        this.period = period;
    }

    public String getSymbols() { return symbols; }
    public String getWeights() { return weights; }
    public String getPeriod() { return period; }
}

