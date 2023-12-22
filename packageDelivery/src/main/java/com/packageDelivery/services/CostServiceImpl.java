package com.packageDelivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageDelivery.entities.Cost;
import com.packageDelivery.repositories.CostRepository;

@Service
public class CostServiceImpl implements CostService {

    @Autowired
    private CostRepository costRepository;

    @Override
    public Cost getGlobalCostConfiguration() {
    
        return costRepository.findById(1L).orElse(null);
    }

    @Override
    public void updateGlobalCostConfiguration(Cost cost) {
   
        costRepository.save(cost);
    }
}
