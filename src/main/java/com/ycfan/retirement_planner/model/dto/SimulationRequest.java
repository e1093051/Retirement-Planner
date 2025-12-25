package com.ycfan.retirement_planner.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SimulationRequest {

    @NotNull @Min(0)
    private int currentAge;
    @NotNull @Min(0)
    private int retirementAge;

    @NotNull @Min(0)
    private double currentSavings;

    // Contribution added once per year
    @NotNull @Min(0)
    private double yearlyContribution;

    private String riskProfile;   // "conservative", "balanced", "aggressive"
    @Min(1)
    private Integer numSimulations;
    @NotNull @Min(0)
    private double targetAmount;

    public SimulationRequest() {
        this.numSimulations = 1000;
        this.riskProfile = "balanced";
    }

    public int getCurrentAge() { return currentAge; }
    public void setCurrentAge(int currentAge) { this.currentAge = currentAge; }

    public int getRetirementAge() { return retirementAge; }
    public void setRetirementAge(int retirementAge) { this.retirementAge = retirementAge; }

    public double getCurrentSavings() { return currentSavings; }
    public void setCurrentSavings(double currentSavings) { this.currentSavings = currentSavings; }

    public double getYearlyContribution() { return yearlyContribution; }
    public void setYearlyContribution(double yearlyContribution) {
        this.yearlyContribution = yearlyContribution;
    }

    public String getRiskProfile() { return riskProfile; }
    public void setRiskProfile(String riskProfile) { this.riskProfile = riskProfile; }

    public int getNumSimulations() { return numSimulations; }
    public void setNumSimulations(int numSimulations) { this.numSimulations = numSimulations; }

    public double getTargetAmount() { return targetAmount; }
    public void setTargetAmount(double targetAmount) { this.targetAmount = targetAmount; }
}


