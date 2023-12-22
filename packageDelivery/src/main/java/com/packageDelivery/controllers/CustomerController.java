/**
 * 
 */
package com.packageDelivery.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.packageDelivery.entities.Customer;
import com.packageDelivery.entities.Delivery;
import com.packageDelivery.extras.LoginData;
import com.packageDelivery.services.CustomerService;

/**
 * @author Acer
 *
 */

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/")
	public List<Customer> getAllCustomer(){
		return customerService.getAll();
	}
	
	@GetMapping("/getById/{id}")
	public Customer getByCustomerId(@PathVariable int id){
		return customerService.getById(id);
	}
	
	@PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
		
		Customer checkedCus = customerService.customerRegisterCheck(customer);
		
		if(checkedCus != null) {
			Customer newCus = customerService.save(checkedCus);
			return new ResponseEntity<Customer>(newCus, HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		
		
		
	}
	
	@GetMapping("/getHistory/{id}")
	public ResponseEntity<List<Delivery>> getHistory(@PathVariable int id){
		Customer cus = customerService.getById(id);
		if(cus != null) {
			List<Delivery> deliveries = cus.getDeliveries();
			return new ResponseEntity<List<Delivery>>(deliveries, HttpStatus.OK);
		}else {
			return new ResponseEntity<> (null, HttpStatus.BAD_REQUEST); 
		}
	}
	
	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Customer updateCustomer(@RequestBody Customer customer) {
		return customerService.update(customer);
	}
	
	@DeleteMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteCustomer(@RequestBody Customer customer) {
		 customerService.delete(customer);
	}
	
	//login
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> loginCustomer(@RequestBody LoginData cusLogin) {
	    Customer existingCustomer = customerService.customerLoginCheck(cusLogin);

	    if (existingCustomer != null) {
	        return new ResponseEntity<Customer>(existingCustomer, HttpStatus.ACCEPTED);
	    } else {
	        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
	    }
	}


	
}
