package com.ycfan.retirement_planner.model.domain;

import java.util.List;

public class PathResult {

    // Wealth values for each simulated year, including the initial state (year 0)
    private List<Double> path;

    // Final wealth value at the end of the simulation
    private double finalWealth;

    public PathResult() {
        // Default constructor for frameworks
    }

    public PathResult(List<Double> path, double finalWealth) {
        this.path = path;
        this.finalWealth = finalWealth;
    }

    public List<Double> getPath() { return path; }
    public void setPath(List<Double> path) { this.path = path; }

    public double getFinalWealth() { return finalWealth; }
    public void setFinalWealth(double finalWealth) { this.finalWealth = finalWealth; }
}

