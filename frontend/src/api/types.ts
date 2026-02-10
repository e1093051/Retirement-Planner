export type RiskProfileKey = "conservative" | "balanced" | "aggressive";

export interface SimulateRequest {
  currentAge: number;
  retirementAge: number;
  currentSavings: number;
  yearlyContribution: number;
  targetAmount: number;
  numSimulations: number;
  riskProfile: RiskProfileKey;
}

export interface SimulateResponse {
  numSimulations: number;
  targetAmount: number;
  medianWealth: number;
  p10Wealth: number;
  p90Wealth: number;
  probabilityReachTarget: number;
  samplePaths?: number[][];
}

export type RiskProfilesResponse = Record<
  RiskProfileKey,
  { mean: number; volatility: number }
>;

export type AllocationInfo = { symbols: string; weights: string; period: string };
export type AllocationMap = Record<"conservative" | "balanced" | "aggressive", AllocationInfo>;

