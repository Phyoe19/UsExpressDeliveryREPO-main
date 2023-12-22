/**
 * 
 */
package com.packageDelivery.filterEntities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class PreDelivery {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int preDeliId;
	
	private String receiverName;
	private String receiverPhone;
	private double itemWeight;
	private double distance;
	private double cost;
	private double pickupLat;
	private double pickupLng;
	private double destinationLat;
	private double destinationLng;
	private String pickupAddressText;
	private String destinationAddressText;
	private int postalCode;
	private int customerId;
	public Object getPickupLat() {
		// TODO Auto-generated method stub
		return null;
	}
	public Object getPickupLng() {
		// TODO Auto-generated method stub
		return null;
	}
	public int getCustomerId() {
		// TODO Auto-generated method stub
		return 0;
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
	public Object getCost() {
		// TODO Auto-generated method stub
		return null;
	}
	public Object getDistance() {
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
	public double getPostalCode() {
		// TODO Auto-generated method stub
		return 0;
	}
}
