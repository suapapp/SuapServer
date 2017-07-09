/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Galimberti
 */
public class DistanceCalculator
{
	public static final int UNIT_MILES      = 0;
	public static final int UNIT_KILOMETERS = 1;

	public static double distance(double lat1, double lon1, double lat2, double lon2, int unit) 
    {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		
        if (unit == UNIT_KILOMETERS) 
        {
			dist = dist * 1.609344;
		}
        
		return (dist);
	}

	private static double deg2rad(double deg) 
    {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) 
    {
		return (rad * 180 / Math.PI);
	}
}
