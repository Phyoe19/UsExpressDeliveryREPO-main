/**
 * 
 */
package com.packageDelivery.services;

import java.util.List;

import com.packageDelivery.entities.Admin;
import com.packageDelivery.extras.LoginData;

/**
 * @author Acer
 *
 */
public interface AdminService {
	public Admin getById(int id);
	public Admin save(Admin admin);
	public Admin update(Admin admin);
	public void delete(Admin admin);
	public List<Admin> getAll();
	public Admin adminLoginCheck(LoginData loginData);
	public Admin adminRegisterCheck(Admin admin);
}
