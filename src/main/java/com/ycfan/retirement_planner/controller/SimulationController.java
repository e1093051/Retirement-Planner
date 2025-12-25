package com.ycfan.retirement_planner.controller;

import com.ycfan.retirement_planner.model.dto.SimulationRequest;
import com.ycfan.retirement_planner.model.dto.SimulationResult;
import com.ycfan.retirement_planner.service.SimulationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SimulationController {

    private static final Logger log =
            LoggerFactory.getLogger(SimulationController.class);

    private final SimulationService simulationService;

    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    // Runs retirement Monte Carlo simulation
    @PostMapping("/simulate")
    public SimulationResult simulate(@Valid @RequestBody SimulationRequest req) {
        log.info(
                "POST /api/simulate | currentAge={} retirementAge={} yearlyContribution={} riskProfile={} numSimulations={}",
                req.getCurrentAge(),
                req.getRetirementAge(),
                req.getYearlyContribution(),
                req.getRiskProfile(),
                req.getNumSimulations()
        );
        return simulationService.runMonteCarlo(req);
    }
}
