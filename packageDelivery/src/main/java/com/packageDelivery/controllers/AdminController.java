/**
 * 
 */
package com.packageDelivery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.packageDelivery.entities.Admin;
import com.packageDelivery.extras.LoginData;
import com.packageDelivery.services.AdminService;

/**
 * @author Acer
 *
 */

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	
	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> loginAdmin(@RequestBody LoginData riderLogin) {
		Admin existingAdmin = adminService.adminLoginCheck(riderLogin);

		if (existingAdmin != null) {
			return new ResponseEntity<Admin>(existingAdmin, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

}
