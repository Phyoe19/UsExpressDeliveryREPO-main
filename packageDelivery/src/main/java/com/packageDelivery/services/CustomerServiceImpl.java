/**
 * 
 */
package com.packageDelivery.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.packageDelivery.entities.Customer;
import com.packageDelivery.extras.LoginData;
import com.packageDelivery.repositories.CustomerRepo;

/**
 * @author Acer
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Override
	public Customer getById(int id) {
		// TODO Auto-generated method stub
		return customerRepo.findById(id).orElse(null);
	}
	
	@Override
	public Customer save(Customer cus) {
		// TODO Auto-generated method stub
		return customerRepo.save(cus);
	}
	
	@Override
	public Customer update(Customer cus) {
		// TODO Auto-generated method stub
		Customer existingCus = getById(cus.getCustomerId());
		existingCus.setEmail(cus.getEmail());
		existingCus.setName(cus.getName());
		existingCus.setPassword(cus.getPassword());
		existingCus.setAddress(cus.getAddress());
		existingCus.setPhoneNumber(cus.getPhoneNumber());
		
		Customer updatedCus = save(existingCus);
		
		return updatedCus;
	}
	
	@Override
	public void delete(Customer cus) {
		// TODO Auto-generated method stub
		
		Customer cusToDel = getById(cus.getCustomerId());
		
		customerRepo.delete(cusToDel);
		
	}
	
	@Override
	public List<Customer> getAll() {
		// TODO Auto-generated method stub
		return customerRepo.findAll();
	}

	@Override
	public Customer customerLoginCheck(LoginData loginData) {
	    List<Customer> customers = getAll();

	    // Filter through all customers
	    List<Customer> filterCustomers = customers.stream()
	            .filter(cus -> cus.getName().equals(loginData.getUsername()) && cus.getPassword().equals(loginData.getPassword()))
	            .collect(Collectors.toList());

	    if (filterCustomers.size() == 1) {
	        return filterCustomers.get(0); // Return the customer if found
	    } else {
	        return null; // Return null if no customer is found or multiple customers match the criteria
	    }
	}

	@Override
	public Customer customerRegisterCheck(Customer cus) {
		// TODO Auto-generated method stub
		
		 List<Customer> existingName = getAll().stream().filter(customer -> customer.getName().equals(cus.getName())).collect(Collectors.toList());
		
		if(existingName.size() > 0) {
			return null;
		}else {
			
		return cus;
		}
	}


}













