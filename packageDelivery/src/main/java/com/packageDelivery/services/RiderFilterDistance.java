/**
 * 
 */
package com.packageDelivery.services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.packageDelivery.entities.Rider;

/**
 * @author Acer
 *
 */
public class RiderFilterDistance {

	public List<Rider> findNearRiders(List<Rider> riders, Object dLat, Object dLng) {
		List<Rider> filteredRiders = new ArrayList<>();

		for (Rider rider : riders) {
			double distance = findDistance( dLat, dLng, rider.getLatitude(), rider.getLongitude());
			DecimalFormat df = new DecimalFormat("#.##");
			String formattedDistance = df.format(distance);

			double distanceValue = Double.parseDouble(formattedDistance);

			double distanceRange = 3.00;

			if (distanceValue <= distanceRange) {

				filteredRiders.add(rider);
			}
		}
		return filteredRiders;

	}

	private double findDistance(Object dLat, Object dLng, double latitude, double longitude) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static double findDistance( double dLat, double dLng, double riderLat, double riderLng) {
		double earthRadius = 6378;// km

		double userLatRad = Math.toRadians(dLat);
		double userLngRad = Math.toRadians(dLng);
		double riderLatRad = Math.toRadians(riderLat);
		double riderLngRad = Math.toRadians(riderLng);

		double lngDiff = riderLngRad - userLngRad;
		double latDiff = riderLatRad - userLatRad;

		double a = Math.pow(Math.sin(latDiff / 2), 2)
				+ Math.cos(userLatRad) * Math.cos(riderLatRad) * Math.pow(Math.sin(lngDiff / 2), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return earthRadius * c;// km
	}

}
