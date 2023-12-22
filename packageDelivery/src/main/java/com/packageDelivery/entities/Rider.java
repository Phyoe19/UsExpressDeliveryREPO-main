/**
 * 
 */
package com.packageDelivery.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Rider {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int riderId;
	
	private String name;
	private String email;
	private String password;
	private String phoneNumber;
	private String address;
	
	private double latitude;
	private double longitude;
	
	private int postalCode;
	
	private Status status;
	
	private DeliStatus deliStatus;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(255) default 'RIDER'")
	private Role role;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "rider")
	private List<Delivery> deliveries;

	public int getRiderId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Delivery> getDeliveries() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLatitude(Object latitude2) {
		// TODO Auto-generated method stub
		
	}

	public void setLongitude(Object latitude2) {
		// TODO Auto-generated method stub
		
	}

	public void setPostalCode(Object postalCode2) {
		// TODO Auto-generated method stub
		
	}

	public void setStatus(Object status2) {
		// TODO Auto-generated method stub
		
	}

	public void setDeliStatus(Object deliStatus2) {
		// TODO Auto-generated method stub
		
	}

	public double getLatitude() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getLongitude() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getPostalCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getPhoneNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getDeliStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setName(Object name2) {
		// TODO Auto-generated method stub
		
	}

	public void setEmail(Object email2) {
		// TODO Auto-generated method stub
		
	}

	public void setPassword(Object password2) {
		// TODO Auto-generated method stub
		
	}

	public void setPhoneNumber(Object phoneNumber2) {
		// TODO Auto-generated method stub
		
	}

	public void setAddress(Object address2) {
		// TODO Auto-generated method stub
		
	}


}
