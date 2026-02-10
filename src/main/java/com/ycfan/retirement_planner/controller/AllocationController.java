package com.ycfan.retirement_planner.controller;

import com.ycfan.retirement_planner.model.dto.AllocationInfo;
import com.ycfan.retirement_planner.service.PortfolioAllocationProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AllocationController {

    private final PortfolioAllocationProvider allocationProvider;

    public AllocationController(PortfolioAllocationProvider allocationProvider) {
        this.allocationProvider = allocationProvider;
    }

    @GetMapping("/api/allocations")
    public Map<String, AllocationInfo> getAllocations() {
        return allocationProvider.getAllDefaultAllocations();
    }
}