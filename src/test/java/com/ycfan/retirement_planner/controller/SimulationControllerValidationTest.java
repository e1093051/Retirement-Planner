package com.ycfan.retirement_planner.controller;

import com.ycfan.retirement_planner.model.dto.SimulationRequest;
import com.ycfan.retirement_planner.model.dto.SimulationResult;
import com.ycfan.retirement_planner.service.SimulationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SimulationController.class)
class SimulationControllerValidationTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean SimulationService simulationService;

    @Test
    void simulate_invalidNumSimulations_returns400_andDoesNotCallService() throws Exception {
        String json = """
        {
          "currentAge": 25,
          "retirementAge": 65,
          "currentSavings": 10000,
          "yearlyContribution": 12000,
          "riskProfile": "balanced",
          "numSimulations": 0,
          "targetAmount": 1000000
        }
        """;

        mockMvc.perform(post("/api/simulate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(simulationService);
    }

    @Test
    void simulate_missingRequiredField_returns400_andDoesNotCallService() throws Exception {
        String json = """
        {
          "currentAge": 25,
          "retirementAge": 65,
          "currentSavings": 10000,
          "yearlyContribution": 12000,
          "riskProfile": "balanced",
          "numSimulations": 100,
        }
        """;

        mockMvc.perform(post("/api/simulate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(simulationService);
    }

    @Test
    void simulate_invalidJsonType_returns400_andDoesNotCallService() throws Exception {
        String json = """
    {
      "currentAge": "abc",
      "retirementAge": 65,
      "currentSavings": 10000,
      "yearlyContribution": 12000,
      "riskProfile": "balanced",
      "numSimulations": 10,
      "targetAmount": 1000000
    }
    """;

        mockMvc.perform(post("/api/simulate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(simulationService);
    }

    @Test
    void simulate_validRequest_returns200_andJson() throws Exception {
        SimulationRequest req = new SimulationRequest();
        req.setCurrentAge(25);
        req.setRetirementAge(65);
        req.setCurrentSavings(10000.0);
        req.setYearlyContribution(12000.0);
        req.setRiskProfile("balanced");
        req.setNumSimulations(10);
        req.setTargetAmount(1000000.0);

        SimulationResult fake = new SimulationResult();
        fake.setNumSimulations(10);
        fake.setTargetAmount(1000000.0);
        fake.setMedianWealth(123.0);
        fake.setP10Wealth(100.0);
        fake.setP90Wealth(200.0);
        fake.setProbabilityReachTarget(0.5);

        when(simulationService.runMonteCarlo(any(SimulationRequest.class))).thenReturn(fake);

        mockMvc.perform(post("/api/simulate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.numSimulations").value(10))
                .andExpect(jsonPath("$.probabilityReachTarget").value(0.5));
    }
}

