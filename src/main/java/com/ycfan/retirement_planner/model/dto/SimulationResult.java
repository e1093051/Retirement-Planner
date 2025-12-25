package com.ycfan.retirement_planner.model.dto;

import java.util.List;

public class SimulationResult {

    // Total number of Monte Carlo simulations executed
    private int numSimulations;

    // The user-provided target amount for retirement
    private double targetAmount;

    // Summary statistics across all simulation final wealth values
    private double medianWealth;
    private double p10Wealth;   // 10th percentile
    private double p90Wealth;   // 90th percentile

    // Probability (0–1) that the final wealth reaches or exceeds the target
    private double probabilityReachTarget;

    // A few selected simulation paths for visualization
    private List<List<Double>> samplePaths;

    public SimulationResult() {}

    public SimulationResult(int numSimulations,
                            double targetAmount,
                            double medianWealth,
                            double p10Wealth,
                            double p90Wealth,
                            double probabilityReachTarget,
                            List<List<Double>> samplePaths) {
        this.numSimulations = numSimulations;
        this.targetAmount = targetAmount;
        this.medianWealth = medianWealth;
        this.p10Wealth = p10Wealth;
        this.p90Wealth = p90Wealth;
        this.probabilityReachTarget = probabilityReachTarget;
        this.samplePaths = samplePaths;
    }

    public int getNumSimulations() { return numSimulations; }
    public void setNumSimulations(int numSimulations) { this.numSimulations = numSimulations; }

    public double getTargetAmount() { return targetAmount; }
    public void setTargetAmount(double targetAmount) { this.targetAmount = targetAmount; }

    public double getMedianWealth() { return medianWealth; }
    public void setMedianWealth(double medianWealth) { this.medianWealth = medianWealth; }

    public double getP10Wealth() { return p10Wealth; }
    public void setP10Wealth(double p10Wealth) { this.p10Wealth = p10Wealth; }

    public double getP90Wealth() { return p90Wealth; }
    public void setP90Wealth(double p90Wealth) { this.p90Wealth = p90Wealth; }

    public double getProbabilityReachTarget() { return probabilityReachTarget; }
    public void setProbabilityReachTarget(double probabilityReachTarget) { this.probabilityReachTarget = probabilityReachTarget; }

    public List<List<Double>> getSamplePaths() { return samplePaths; }
    public void setSamplePaths(List<List<Double>> samplePaths) { this.samplePaths = samplePaths; }
}
