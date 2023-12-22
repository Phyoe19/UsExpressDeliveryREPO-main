/**
 * 
 */
package com.packageDelivery.filterEntities;

import com.packageDelivery.entities.Delivery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Acer
 *
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TrackDeliveryDTO {
	private Delivery delivery;
	private String text;
	public void setText(String string) {
		// TODO Auto-generated method stub
		
	}
	public void setDelivery(Object object) {
		// TODO Auto-generated method stub
		
	}
}
