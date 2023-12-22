/**
 * 
 */
package com.packageDelivery.services;

import java.util.List;


import com.packageDelivery.entities.Customer;
import com.packageDelivery.extras.LoginData;

/**
 * @author Acer
 *
 */
public interface CustomerService {
	public Customer getById(int id);
	public Customer save(Customer cus);
	public Customer update(Customer cus);
	public void delete(Customer cus);
	public List<Customer> getAll();
	
	public Customer customerLoginCheck(LoginData loginData);
	public Customer customerRegisterCheck(Customer cus);
}
