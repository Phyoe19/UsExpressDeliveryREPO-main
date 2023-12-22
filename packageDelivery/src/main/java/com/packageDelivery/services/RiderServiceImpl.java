/**
 * 
 */
package com.packageDelivery.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageDelivery.entities.DeliStatus;
import com.packageDelivery.entities.Rider;
import com.packageDelivery.entities.Status;
import com.packageDelivery.extras.LoginData;
import com.packageDelivery.repositories.RiderRepo;

/**
 * @author Acer
 *
 */

@Service
public abstract class RiderServiceImpl implements RiderService {

	@Autowired
	RiderRepo riderRepo;

	@Override
	public Rider getById(int id) {
		// TODO Auto-generated method stub
		return riderRepo.findById(id).orElse(null);
	}

	@Override
	public Rider save(Rider rider) {
		// TODO Auto-generated method stub
		return riderRepo.save(rider);
	}

	@Override
	public Rider update(Rider rider) {
		// TODO Auto-generated method stub

		Rider existingRider = getById(rider.getRiderId());

		existingRider.setName(rider.getName());
		existingRider.setEmail(rider.getEmail());
		existingRider.setPassword(rider.getPassword());
		existingRider.setPhoneNumber(rider.getPhoneNumber());
		existingRider.setAddress(rider.getAddress());
		
		existingRider.setDeliStatus(rider.getDeliStatus());

		existingRider.setLatitude(rider.getLatitude());
		existingRider.setLongitude(rider.getLongitude());

		existingRider.setPostalCode(rider.getPostalCode());
		existingRider.setStatus(rider.getStatus());

		Rider updatedRider = save(existingRider);

		return updatedRider;
	}

	@Override
	public void delete(Rider rider) {
		// TODO Auto-generated method stub
		Rider riderToDel = getById(rider.getRiderId());
		riderRepo.delete(riderToDel);
	}

	@Override
	public List<Rider> getAll() {
		// TODO Auto-generated method stub
		return riderRepo.findAll();
	}

	@Override
	public List<Rider> getSpecificStatusRiders(char status) {
		// TODO Auto-generated method stub
		if (status == 'O') {
			List<Rider> onlineRiders = getAll().stream().filter(rider -> Status.ONLINE.equals(rider.getStatus()))
					.collect(Collectors.toList());

			return onlineRiders;
		} else if (status == 'F') {
			List<Rider> offlineRiders = getAll().stream().filter(rider -> Status.OFFLINE.equals(rider.getStatus()))
					.collect(Collectors.toList());
			return offlineRiders;
		} else {
			return getAll();
		}

	}
@Override
	public List<Rider> getRidersByPostalCode(int postalCode) {
		// TODO Auto-generated method stub
		List<Rider> onlineRiders = getSpecificStatusRiders('O');

		List<Rider> filteredRiders = onlineRiders.stream().filter(rider -> rider.getPostalCode() == postalCode && DeliStatus.FREE.equals(rider.getDeliStatus()) )
				.collect(Collectors.toList());

		return filteredRiders;
	}

	@Override
	public Rider riderLoginCheck(LoginData loginData) {
		// TODO Auto-generated method stub

		List<Rider> riders = getAll();

		List<Rider> filteredRiders = riders.stream().filter(rider -> rider.getName().equals(loginData.getUsername())
				&& rider.getPassword().equals(loginData.getPassword())).collect(Collectors.toList());

		if (filteredRiders.size() == 1) {
			return filteredRiders.get(0);
		} else {

			return null;
		}
	}

	@Override
	public Rider riderRegisterCheck(Rider rider) {
		// TODO Auto-generated method stub
		 List<Rider> existingName = getAll().stream().filter(r -> r.getName().equals(rider.getName())).collect(Collectors.toList());
			
		if(existingName.size() > 0) {
			return null;
		}else {
			
		return rider;
		}
	}

	@Override
	public List<Rider> getRidersByPostalCode(Object postalCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
