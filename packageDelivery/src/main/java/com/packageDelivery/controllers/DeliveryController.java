/**
 * 
 */
package com.packageDelivery.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.packageDelivery.entities.ConfirmDeliveryStatus;
import com.packageDelivery.entities.Customer;
import com.packageDelivery.entities.Delivery;
import com.packageDelivery.entities.Rider;
import com.packageDelivery.filterEntities.ConfirmDelivery;
import com.packageDelivery.filterEntities.GetNearRidersResponse;
import com.packageDelivery.filterEntities.PreDelivery;
import com.packageDelivery.filterEntities.PreDeliveryService;
import com.packageDelivery.filterEntities.TrackDeliveryDTO;
import com.packageDelivery.services.CustomerService;
import com.packageDelivery.services.DeliveryService;
import com.packageDelivery.services.RiderService;

/**
 * @author Acer
 *
 */

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
	
	@Autowired
	PreDeliveryService preDeliService;

	@Autowired
	DeliveryService deliveryService;
	
	@Autowired
	RiderService riderService;
	
	@Autowired
	CustomerService cusService;
	
	@Autowired
	SimpMessagingTemplate messageTemplate;
	
	@GetMapping("/")
	public List<Delivery> getAllDelivery(){
		return deliveryService.getAll();
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<Delivery> getByDeliveryId(@PathVariable int id){
		
		Delivery deli = deliveryService.getById(id);
		
		if(deli != null) {
			return new ResponseEntity<Delivery>(deli, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/trackDelivery/{id}")
	public ResponseEntity<TrackDeliveryDTO> trackDeliveryByDeliveryId(@PathVariable int id){
		
		Delivery deli = deliveryService.getById(id);
		
		TrackDeliveryDTO td = new TrackDeliveryDTO();
		
		if(deli != null) {
			if( ConfirmDeliveryStatus.COMPLETED.equals(deli.getCdStatus())) {
				
				td.setText("Delivery Completed");
				td.setDelivery(null);
				
				return new ResponseEntity<TrackDeliveryDTO> (td, HttpStatus.OK);
				
			}
			else if(ConfirmDeliveryStatus.ONGOING.equals(deli.getCdStatus())) {
				
				td.setDelivery(deli);
				td.setText("please wait to track");
				
				return new ResponseEntity<TrackDeliveryDTO>(td, HttpStatus.OK);
			}
			else{
				
				td.setText("Delivery Failed");
				td.setDelivery(null);
				
				return new ResponseEntity<TrackDeliveryDTO>(td, HttpStatus.OK);
			}
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
			
	}
	
	
	@PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Delivery saveDelivery(@RequestBody Delivery deli) {
		return deliveryService.save(deli);
	}
	
	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Delivery> updateDelivery(@RequestBody Delivery deli) {
		Delivery deliToBeUpdated = deliveryService.getById(deli.getDeliveryId());
		if(deliToBeUpdated != null) {
			return new ResponseEntity<Delivery>(deliveryService.update(deli), HttpStatus.ACCEPTED);
		}
		
		else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String deleteDelivery(@RequestBody Delivery deli) {

		Delivery deliToDel = deliveryService.getById(deli.getDeliveryId());
		
		if(deliToDel.equals(null)) {
			return "Delivery is not on the list";
		}else {
			deliveryService.delete(deliToDel);
			return "Delivery Id: " + deliToDel.getDeliveryId()+ " is deleted";
		}

	}
	
	@GetMapping("/getPreDelivery/{id}")
	public ResponseEntity<PreDelivery> getPreDelivery(@PathVariable int id) {
		
		PreDelivery preDeli = preDeliService.getById(id);
		
		if(preDeli != null) {
			return new ResponseEntity<PreDelivery>(preDeli, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getNearRiders/{id}")
	public ResponseEntity<GetNearRidersResponse> getNearRiders(@PathVariable int id){
		PreDelivery preDelivery = preDeliService.getById(id);
		
		if(preDelivery != null) {
			List<Rider> nearRiders = deliveryService.getNearRiders(preDelivery);
			
			//send delivery data to all riders
			for(Rider rider : nearRiders) {
				sendRiderDeliveryData(rider.getRiderId(), preDelivery);
			}
			
			GetNearRidersResponse resp = new GetNearRidersResponse();
			
			resp.setPickupLat(preDelivery.getPickupLat());
			resp.setPickupLng(preDelivery.getPickupLng());
			resp.setNearRiders(nearRiders);
			
			return new ResponseEntity<GetNearRidersResponse>(resp, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	public void sendRiderDeliveryData(int riderId, PreDelivery preDeli) {
		System.out.println("rider is send data");
		String destinationUrl = "/package-delivery/rider/get-pre-delivery/"+riderId;
		messageTemplate.convertAndSend(destinationUrl, preDeli);
		System.out.println(destinationUrl);
		System.out.println("rider is send data");
	}
	
	////
	@PostMapping(value = "/pre/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PreDelivery> savePreDelivery(@RequestBody PreDelivery preDeli) {
		
		Customer customer = cusService.getById(preDeli.getCustomerId());
		if(customer != null) {
		  PreDelivery pDeli = preDeliService.save(preDeli);
		  
		  return new ResponseEntity<PreDelivery>(pDeli, HttpStatus.ACCEPTED);
		 
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}
	
	@PostMapping(value = "/confirm" , consumes = MediaType.APPLICATION_JSON_VALUE)
	public Delivery confirmDelivery(@RequestBody ConfirmDelivery conDeli) {
		
		int riderId = conDeli.getRiderId();
		int preDeliId = conDeli.getPreDeliId();
		
		Rider existingRider = riderService.getById(riderId);
		PreDelivery preDeli = preDeliService.getById(preDeliId);
		
		if(existingRider != null && preDeli != null) {
			Delivery delivery = setDelivery(existingRider, preDeli);
			
			
			
			sendCustomerDelivery(delivery);
			return delivery;
		}else {
			return null;
		}
		
	}
	
	private void sendCustomerDelivery(Delivery delivery) {
		// TODO Auto-generated method stub
		
		System.out.println("Sended Delivery To Customer");
		String destinationUrl = "/package-delivery/customer/get-delivery/";
		messageTemplate.convertAndSend(destinationUrl, delivery);
		
	}

	public Delivery setDelivery(Rider rider, PreDelivery preDeli) {
		Delivery delivery = new Delivery();
		delivery.setReceiverName(preDeli.getReceiverName());
		delivery.setReceiverPhone(preDeli.getReceiverPhone());
		delivery.setItemWeight(preDeli.getItemWeight());
		delivery.setCost(preDeli.getCost());
		delivery.setDistance(preDeli.getDistance());
		delivery.setPickupLat(preDeli.getPickupLat());
		delivery.setPickupLng(preDeli.getPickupLng());
		delivery.setDestinationLat(preDeli.getDestinationLat());
		delivery.setDestinationLng(preDeli.getDestinationLng());
		delivery.setPickupAddressText(preDeli.getPickupAddressText());
		delivery.setDestinationAddressText(preDeli.getDestinationAddressText());
		delivery.setPostalCode(preDeli.getPostalCode());
		delivery.setCreatedAt(LocalDateTime.now());
		
		delivery.setCdStatus(ConfirmDeliveryStatus.ONGOING);
		
		Customer customer = cusService.getById(preDeli.getCustomerId());
		
		delivery.setCustomer(customer);
		delivery.setRider(rider);
		
		
		return deliveryService.save(delivery);
		
	}
	
}
