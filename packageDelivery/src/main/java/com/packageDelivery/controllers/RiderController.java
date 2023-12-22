/**
 * 
 */
package com.packageDelivery.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
import com.packageDelivery.extras.LoginData;
import com.packageDelivery.filterEntities.PreDelivery;
import com.packageDelivery.filterEntities.PreDeliveryService;
import com.packageDelivery.filterEntities.TrackRiderData;
import com.packageDelivery.services.CustomerService;
import com.packageDelivery.services.DeliveryService;
import com.packageDelivery.services.RiderService;

/**
 * @author Acer
 *
 */

@RestController
@RequestMapping("/rider")
public class RiderController {

	@Autowired
	RiderService riderService;

	@Autowired
	PreDeliveryService preDeliService;

	@Autowired
	CustomerService cusService;

	@Autowired
	DeliveryService deliService;

	@Autowired
	SimpMessagingTemplate messageTemplate;

	@GetMapping("/")
	public List<Rider> getAllRider() {
		return riderService.getAll();
	}
	
	@GetMapping("/delivery-ongoing/{id}")
	public ResponseEntity<Delivery> getDeliveryStatus(@PathVariable int id) {
		Rider rider =riderService.getById(id);
		
		List<Delivery> deliveries = deliService.getDeliveryByRider(rider);
		
		List<Delivery> ongoingDeli = deliveries.stream().filter(deli -> ConfirmDeliveryStatus.ONGOING.equals(deli.getCdStatus())).collect(Collectors.toList());
		
		if(ongoingDeli.size() == 1) {
			return new ResponseEntity<Delivery>(ongoingDeli.get(0),HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
	}

	@GetMapping("/getById/{id}")
	public Rider getByRiderId(@PathVariable int id) {
		return riderService.getById(id);
	}

	@PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rider> saveRider(@RequestBody Rider rider) {
		
		Rider checkedRider = riderService.riderRegisterCheck(rider);
		
		if(checkedRider != null) {


			Rider newRider = riderService.save(checkedRider);
			return new ResponseEntity<Rider>(newRider, HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
	}

	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rider> updateRider(@RequestBody Rider rider) {
		
		Rider riderToBeUpdate = riderService.getById(rider.getRiderId());
		
		if(riderToBeUpdate != null) {
			return new ResponseEntity<Rider>(riderService.update(rider), HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
	}

	@DeleteMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteRider(@RequestBody Rider rider) {
		riderService.delete(rider);
	}

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rider> loginRider(@RequestBody LoginData riderLogin) {
		Rider existingRider = riderService.riderLoginCheck(riderLogin);

		if (existingRider != null) {
			return new ResponseEntity<Rider>(existingRider, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@MessageMapping("/track-rider")
	public void trackRider(TrackRiderData trackRiderData) {
		
		

		if (trackRiderData.getDeliveryId() > 0) {
			System.out.println("DeliveryId not zero");
			//int preDeliId = trackRiderData.getPreDeli().getPreDeliId();
			//PreDelivery preDeli = preDeliService.getById(preDeliId);

			Rider rider = updateRiderLocation(trackRiderData);

			int deliveryId = trackRiderData.getDeliveryId();
			
			sendDeliveryBackToCustomer(deliveryId, rider);

		}
		// send location to admin
		updateRiderLocation(trackRiderData);
		sendRiderLocationToAdmin();
		System.out.println("admin is sent riderdata");
	}
	
	
	@MessageMapping("/complete-Delivery")
	public void completeDelivery(Delivery deli) {
		Delivery delivery = deliService.getById(deli.getDeliveryId());
		if(delivery != null) {
			sendCompleteDeliveryBackToCustomer(delivery);
			System.out.println("Customer is sent completed delivery");
		}
	}
	
	
	@GetMapping("/getHistory/{id}")
	public ResponseEntity<List<Delivery>> getHistory(@PathVariable int id){
		Rider rider = riderService.getById(id);
		if(rider != null) {
			List<Delivery> deliveries = rider.getDeliveries();
			return new ResponseEntity<List<Delivery>>(deliveries, HttpStatus.OK);
		}else {
			return new ResponseEntity<> (null, HttpStatus.BAD_REQUEST); 
		}
	}


	public Delivery saveDeli(PreDelivery preDeli, Rider rider) {
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

		Customer customer = cusService.getById(preDeli.getCustomerId());

		delivery.setCustomer(customer);
		delivery.setRider(rider);

		return deliService.save(delivery);
	}

	public Rider updateRiderLocation(TrackRiderData trackRiderData) {
		Rider existingRider = riderService.getById(trackRiderData.getRiderId());

		existingRider.setLatitude(trackRiderData.getLatitude());
		existingRider.setLongitude(trackRiderData.getLongitude());
		existingRider.setPostalCode(trackRiderData.getPostalCode());
		existingRider.setStatus(trackRiderData.getStatus());
		existingRider.setDeliStatus(trackRiderData.getDeliStatus());

		return riderService.save(existingRider);

	}

	public void sendDeliveryBackToCustomer(int deliveryId, Rider rider) {
		String destinationUrl = "/package-delivery/rider/track-rider/" + deliveryId;
		messageTemplate.convertAndSend(destinationUrl, rider);
	}

	public void sendRiderLocationToAdmin() {
		List<Rider> onlineRiders = riderService.getSpecificStatusRiders('O');

		String destinationUrl = "/package-delivery/admin/track-rider";
		messageTemplate.convertAndSend(destinationUrl, onlineRiders);

	}
	
	public void sendCompleteDeliveryBackToCustomer(Delivery delivery) {
		String destinationUrl = "/package-delivery/rider-complete-delivery/" + delivery.getDeliveryId();
		messageTemplate.convertAndSend(destinationUrl, delivery);
	}

	

}
