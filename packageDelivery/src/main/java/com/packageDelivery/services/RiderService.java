/**
 * 
 */
package com.packageDelivery.services;

import java.util.List;


import com.packageDelivery.entities.Rider;
import com.packageDelivery.extras.LoginData;

/**
 * @author Acer
 *
 */
public interface RiderService {
	public Rider getById(int id);
	public Rider save(Rider rider);
	public Rider update(Rider rider);
	public void delete(Rider rider);
	public List<Rider> getAll();
	
	public List<Rider> getSpecificStatusRiders(char status);//O for online & F for offline
	public List<Rider> getRidersByPostalCode(Object postalCode);
	
	public Rider riderLoginCheck(LoginData loginData);
	public Rider riderRegisterCheck(Rider rider);
	List<Rider> getRidersByPostalCode(int postalCode);
	
}
