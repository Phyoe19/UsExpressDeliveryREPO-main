package com.packageDelivery.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double costPerWeight;
    private double discountPercentage;
    private double costPerDistance;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getCostPerWeight() {
		return costPerWeight;
	}
	public void setCostPerWeight(double costPerWeight) {
		this.costPerWeight = costPerWeight;
	}
	public double getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	public double getCostPerDistance() {
		return costPerDistance;
	}
	public void setCostPerDistance(double costPerDistance) {
		this.costPerDistance = costPerDistance;
	}
    
}
