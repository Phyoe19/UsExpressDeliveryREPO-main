package com.packageDelivery.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class CostController {

    @PostMapping("/calculate")
    public ResponseEntity<Double> calculateTotalCost(@RequestBody CostRequest request) {

        double totalCost = calculateCost(request.getKG(), request.getPercent(), request.getKM());
        return ResponseEntity.ok(totalCost);
    }

    private double calculateCost(double KG, double percent, double KM) {

        double discountedCost = KG * KM * (1 - percent / 100);
        return discountedCost;
    }


    public static class CostRequest {
        private double KG;
        private double percent;
        private double KM;

        public double getKG() {
            return KG;
        }

        public void setKG(double KG) {
            this.KG = KG;
        }

        public double getPercent() {
            return percent;
        }

        public void setPercent(double percent) {
            this.percent = percent;
        }

        public double getKM() {
            return KM;
        }

        public void setKM(double KM) {
            this.KM = KM;
        }
    }
}
