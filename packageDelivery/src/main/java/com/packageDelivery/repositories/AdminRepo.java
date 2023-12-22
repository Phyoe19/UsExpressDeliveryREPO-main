/**
 * 
 */
package com.packageDelivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.packageDelivery.entities.Admin;

/**
 * @author Acer
 *
 */

@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer>{

}
