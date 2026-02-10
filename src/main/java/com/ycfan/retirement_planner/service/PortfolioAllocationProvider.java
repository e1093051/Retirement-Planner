package com.ycfan.retirement_planner.service;

import com.ycfan.retirement_planner.model.dto.Allocation;
import com.ycfan.retirement_planner.model.dto.AllocationInfo;

import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;



/**
 * Maps risk profile -> predefined asset allocation.
 * Only provides configuration. Does NOT call external services.
 */
@Component
public class PortfolioAllocationProvider {

    private static final String EQUITY = "VOO";
    private static final String BOND = "AGG";
    private static final String DEFAULT_PERIOD = "10y";

    public Allocation allocationFor(String riskProfile) {
        String key = (riskProfile == null) ? "balanced" : riskProfile.toLowerCase(Locale.ROOT);

        switch (key) {
            case "conservative":
                return allocationBondEquity(0.70, 0.30);

            case "aggressive":
                return allocationBondEquity(0.10, 0.90);

            case "balanced":
            case "moderate":
            default:
                return allocationBondEquity(0.40, 0.60);
        }
    }

    private Allocation allocationBondEquity(double bondWeight, double equityWeight) {
        String symbols = BOND + "," + EQUITY;
        String weights = formatWeight(bondWeight) + "," + formatWeight(equityWeight);
        return new Allocation(symbols, weights, DEFAULT_PERIOD);
    }

    private String formatWeight(double w) {
        return String.format(Locale.ROOT, "%.4f", w);
    }

    public Map<String, AllocationInfo> getAllDefaultAllocations() {
        var types = List.of("conservative", "balanced", "aggressive");
        Map<String, AllocationInfo> out = new LinkedHashMap<>();

        for (String type : types) {
            var a = this.allocationFor(type);
            out.put(type, new AllocationInfo(a.getSymbols(), a.getWeights(), a.getPeriod()));
        }
        return out;
    }
}
