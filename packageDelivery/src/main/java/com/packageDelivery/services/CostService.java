package com.packageDelivery.services;

import com.packageDelivery.entities.Cost;

public interface CostService {
    Cost getGlobalCostConfiguration();
    void updateGlobalCostConfiguration(Cost cost);
}

