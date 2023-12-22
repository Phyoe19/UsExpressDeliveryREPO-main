/**
 * 
 */
package com.packageDelivery.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.packageDelivery.entities.Delivery;
import com.packageDelivery.entities.Rider;

/**
 * @author Acer
 *
 */

@Repository
public interface DeliveryRepo extends JpaRepository<Delivery, Integer>{

	 List<Delivery> findByRider(Rider rider);
}
