/**
 * 
 */
package com.packageDelivery.entities;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Acer
 *
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int deliveryId;
	
	@Column(nullable = false)
	private String receiverName;
	
	@Column(nullable = false)
	private String receiverPhone;
	
	@Column(nullable = false)
	private double itemWeight;
	
	@Column(nullable = false)
	private double distance;
	
	@Column(nullable = true)
	private double cost;
	
	@Column(nullable = false)
	private double pickupLat;
	
	@Column(nullable = false)
	private double pickupLng;
	
	@Column(nullable = false)
	private double destinationLat;
	@Column(nullable = false)
	private double destinationLng;
	
	@Column(nullable = true)
	private String pickupAddressText;
	
	@Column(nullable = true)
	private String destinationAddressText;
	
	@Column(nullable = false)
	private int postalCode;//postal code of pickup address
	
	@Column(nullable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;
	
	private ConfirmDeliveryStatus cdStatus;
	
	//join customer
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="customer_id", referencedColumnName = "customerId")
	private Customer customer;
	
	//join rider
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "rider_id", referencedColumnName = "riderId")
	private Rider rider;

	public Object getCdStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getDeliveryId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setReceiverName(Object receiverName2) {
		// TODO Auto-generated method stub
		
	}

	public void setReceiverPhone(Object receiverPhone2) {
		// TODO Auto-generated method stub
		
	}

	public void setItemWeight(Object itemWeight2) {
		// TODO Auto-generated method stub
		
	}

	public void setCost(Object cost2) {
		// TODO Auto-generated method stub
		
	}

	public void setDistance(Object distance2) {
		// TODO Auto-generated method stub
		
	}

	public void setPickupLat(Object pickupLat2) {
		// TODO Auto-generated method stub
		
	}

	public void setPickupLng(Object pickupLng2) {
		// TODO Auto-generated method stub
		
	}

	public void setDestinationLat(Object destinationLat2) {
		// TODO Auto-generated method stub
		
	}

	public void setDestinationLng(Object destinationLng2) {
		// TODO Auto-generated method stub
		
	}

	public void setPickupAddressText(Object pickupAddressText2) {
		// TODO Auto-generated method stub
		
	}

	public void setDestinationAddressText(Object destinationAddressText2) {
		// TODO Auto-generated method stub
		
	}

	public void setPostalCode(Object postalCode2) {
		// TODO Auto-generated method stub
		
	}

	public void setCreatedAt(LocalDateTime now) {
		// TODO Auto-generated method stub
		
	}

	public void setCdStatus(Object object) {
		// TODO Auto-generated method stub
		
	}

	public void setCustomer(Customer customer2) {
		// TODO Auto-generated method stub
		
	}

	public void setRider(Rider rider2) {
		// TODO Auto-generated method stub
		
	}

	public Customer getCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

	public Rider getRider() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getReceiverName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getReceiverPhone() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getItemWeight() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getDistance() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getCost() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getPickupLat() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getPickupLng() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getDestinationLat() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getDestinationLng() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getPickupAddressText() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getDestinationAddressText() {
		// TODO Auto-generated method stub
		return null;
	}

	public LocalDateTime getCreatedAt() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	

}
