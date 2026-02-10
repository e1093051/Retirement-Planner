package com.ycfan.retirement_planner.service;

import com.ycfan.retirement_planner.model.dto.ReturnVolatilityEstimate;
import com.ycfan.retirement_planner.model.dto.RiskProfileInfo;
import com.ycfan.retirement_planner.service.MarketAssumptionsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RiskAssumptionsService {

    private static final Logger log = LoggerFactory.getLogger(RiskAssumptionsService.class);

    private final PortfolioAllocationProvider allocationProvider;
    private final MarketAssumptionsClient marketClient;
    private final RiskProfileProvider fallbackProvider;

    public RiskAssumptionsService(PortfolioAllocationProvider allocationProvider,
                                  MarketAssumptionsClient marketClient,
                                  RiskProfileProvider fallbackProvider) {
        this.allocationProvider = allocationProvider;
        this.marketClient = marketClient;
        this.fallbackProvider = fallbackProvider;
    }

    public RiskProfileInfo getAssumptions(String riskProfile) {
        var alloc = allocationProvider.allocationFor(riskProfile);

        try {
            ReturnVolatilityEstimate est = marketClient.estimate(
                    alloc.getSymbols(),
                    alloc.getWeights(),
                    alloc.getPeriod()
            );

            if (est == null) {
                throw new IllegalStateException("Market assumptions service returned null");
            }

            // Unify into the type your simulation consumes
            return new RiskProfileInfo(est.getMean(), est.getVolatility());

        } catch (Exception e) {
            log.warn("Market assumptions failed; falling back to static profile. riskProfile={}, reason={}",
                    riskProfile, e.toString());

            // return fallbackProvider.getOrDefault(riskProfile);
            return null;
        }
    }
}
