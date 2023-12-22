/**
 * 
 */
package com.packageDelivery.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.packageDelivery.entities.Customer;


/**
 * @author Acer
 *
 */
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer>{
	
	Customer findByName(String name);

}
