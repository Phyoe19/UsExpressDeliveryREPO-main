/**
 * 
 */
package com.packageDelivery.filterEntities;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author Acer
 *
 */

@Service
public class PreDeliveryService {
	
	@Autowired
	PreDeliveryRepo repo;
	
	public PreDelivery save(PreDelivery preDeli) {
		return repo.save(preDeli);
	}
	
	public PreDelivery getById(int id) {
		return repo.findById(id).orElse(null);
	}
	
}

