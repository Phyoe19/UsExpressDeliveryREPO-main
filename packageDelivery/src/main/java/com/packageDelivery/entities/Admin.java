/**
 * 
 */
package com.packageDelivery.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Acer
 *
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int adminId;
	
	private String name;
	private String email;
	private String password;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(255) default 'ADMIN'")
	private Role role;

	public void setEmail(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setName(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setPassword(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setRole(Role admin) {
		// TODO Auto-generated method stub
		
	}

	public int getAdminId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
