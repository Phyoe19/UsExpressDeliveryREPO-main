/**
 * 
 */
package com.packageDelivery.services;

import java.util.List;

import com.packageDelivery.entities.Delivery;
import com.packageDelivery.entities.Rider;
import com.packageDelivery.filterEntities.PreDelivery;



/**
 * @author Acer
 *
 */
public interface DeliveryService {
	public Delivery getById(int id);
	public Delivery save(Delivery deli);
	public Delivery update(Delivery deli);
	public void delete(Delivery deli);
	public List<Delivery> getAll();
	public List<Delivery> getDeliveryByRider(Rider rider);
	public List<Rider> getNearRiders(PreDelivery preDelivery);
}
