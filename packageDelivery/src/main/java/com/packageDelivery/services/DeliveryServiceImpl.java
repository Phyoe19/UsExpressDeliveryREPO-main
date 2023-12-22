/**
 * 
 */
package com.packageDelivery.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageDelivery.entities.Customer;
import com.packageDelivery.entities.Delivery;
import com.packageDelivery.entities.Rider;
import com.packageDelivery.filterEntities.PreDelivery;
import com.packageDelivery.repositories.DeliveryRepo;

/**
 * @author Acer
 *
 */

@Service
public class DeliveryServiceImpl implements DeliveryService{
	
	@Autowired
	DeliveryRepo deliRepo;
	
	@Autowired
	CustomerService cusService;
	
	@Autowired
	RiderService riderService;
	


	@Override
	public Delivery getById(int id) {
		// TODO Auto-generated method stub
		return deliRepo.findById(id).orElse(null);
	}

	@Override
	public Delivery save(Delivery deli) {
		// TODO Auto-generated method stub
		
		Customer newCus = cusService.save(deli.getCustomer());
		Rider newRider = riderService.save(deli.getRider());
		
		deli.setCustomer(newCus);
		deli.setRider(newRider);
		
		return deliRepo.save(deli);
	}

	@Override
	public Delivery update(Delivery deli) {
		// TODO Auto-generated method stub
		
		Delivery existingDeli = getById(deli.getDeliveryId());
		
		
		//update customer
		Customer customerToBeUpdated = existingDeli.getCustomer();
		Customer updatedCustomer = cusService.update(customerToBeUpdated);
		
		existingDeli.setCustomer(updatedCustomer);
		
		//update rider
		Rider riderToBeUpdated = existingDeli.getRider();
		Rider updatedRider = riderService.update(riderToBeUpdated);
		
		existingDeli.setRider(updatedRider);
		
		//
		existingDeli.setReceiverName(deli.getReceiverName());
		existingDeli.setReceiverPhone(deli.getReceiverPhone());
		existingDeli.setItemWeight(deli.getItemWeight());
		existingDeli.setDistance(deli.getDistance());
		existingDeli.setCost(deli.getCost());
		existingDeli.setPickupLat(deli.getPickupLat());
		existingDeli.setPickupLng(deli.getPickupLng());
		existingDeli.setDestinationLat(deli.getDestinationLat());
		existingDeli.setDestinationLng(deli.getDestinationLng());
		existingDeli.setPickupAddressText(deli.getPickupAddressText());
		existingDeli.setDestinationAddressText(deli.getDestinationAddressText());
		existingDeli.setCreatedAt(deli.getCreatedAt());
		
		existingDeli.setCdStatus(deli.getCdStatus());
		
		Delivery updatedDelivery = save(existingDeli);
		
		return updatedDelivery;
	}
	
	@Override
	public void delete(Delivery deli) {
		// TODO Auto-generated method stub
		Delivery deliveryToDel = getById(deli.getDeliveryId());
		deliRepo.delete(deliveryToDel);
	}

	@Override
	public List<Delivery> getAll() {
		// TODO Auto-generated method stub
		return deliRepo.findAll();
	}

	@Override
	public List<Rider> getNearRiders(PreDelivery preDelivery) {
		// TODO Auto-generated method stub
		Object postalCode = preDelivery.getPostalCode();
		List<Rider>  ridersInArea = riderService.getRidersByPostalCode(postalCode);
		
		Object dLat = preDelivery.getPickupLat();
		Object dLng = preDelivery.getPickupLng();
		
		RiderFilterDistance riderFilterDistance = new RiderFilterDistance();
		List<Rider> nearRiders = riderFilterDistance.findNearRiders(ridersInArea, dLat, dLng);
		
		return nearRiders;
	}

	@Override
	public List<Delivery> getDeliveryByRider(Rider rider) {
		// TODO Auto-generated method stub
		return deliRepo.findByRider(rider);
	}

	
	

}
