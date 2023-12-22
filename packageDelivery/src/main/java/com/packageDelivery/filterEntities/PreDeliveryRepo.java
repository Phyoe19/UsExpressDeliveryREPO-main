/**
 * 
 */
package com.packageDelivery.filterEntities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Acer
 *
 */

@Repository
public interface PreDeliveryRepo extends JpaRepository<PreDelivery, Integer>{

}
