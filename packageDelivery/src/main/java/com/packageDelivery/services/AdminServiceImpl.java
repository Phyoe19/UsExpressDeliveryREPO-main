/**
 * 
 */
package com.packageDelivery.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packageDelivery.entities.Admin;
import com.packageDelivery.extras.LoginData;
import com.packageDelivery.repositories.AdminRepo;

/**
 * @author Acer
 *
 */
@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	AdminRepo adminRepo;

	@Override
	public Admin getById(int id) {
		// TODO Auto-generated method stub
		return adminRepo.findById(id).orElse(null);
	}

	@Override
	public Admin save(Admin admin) {
		// TODO Auto-generated method stub
		return adminRepo.save(admin);
	}

	@Override
	public Admin update(Admin admin) {
		// TODO Auto-generated method stub
		Admin existingAdmin = getById(admin.getAdminId());
		existingAdmin.setEmail(admin.getEmail());
		existingAdmin.setName(admin.getName());
		existingAdmin.setPassword(admin.getPassword());
		
		Admin updatedAdmin = save(existingAdmin);
		
		return updatedAdmin;
	}

	@Override
	public void delete(Admin admin) {
		// TODO Auto-generated method stub
		
		Admin adminToDel = getById(admin.getAdminId());
		
		adminRepo.delete(adminToDel);
		
	}

	@Override
	public List<Admin> getAll() {
		// TODO Auto-generated method stub
		return adminRepo.findAll();
	}
	
	@Override
	public Admin adminLoginCheck(LoginData loginData) {
		// TODO Auto-generated method stub

		List<Admin> admins = getAll();

		List<Admin> filteredAdmin = admins.stream().filter(admin -> admin.getName().equals(loginData.getUsername())
				&& admin.getPassword().equals(loginData.getPassword())).collect(Collectors.toList());

		if (filteredAdmin.size() == 1) {
			return filteredAdmin.get(0);
		} else {

			return null;
		}
	}

	@Override
	public Admin adminRegisterCheck(Admin admin) {
		// TODO Auto-generated method stub
		 List<Admin> existingName = getAll().stream().filter(ad -> ad.getName().equals(admin.getName())).collect(Collectors.toList());
			
		if(existingName.size() > 0) {
			return null;
		}else {
			
		return admin;
		}
	}

}
