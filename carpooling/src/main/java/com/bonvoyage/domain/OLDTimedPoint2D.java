package com.bonvoyage.domain;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;


public class OLDTimedPoint2D implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3893055859466238908L;
	private double latitude;
	private double longitude;
	private long touchTime;
	
	public OLDTimedPoint2D(){};
	
	public OLDTimedPoint2D(double latitude, double longitude, long touchTime) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.touchTime = touchTime;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

	public long getTouchTime() {
		return touchTime;
	}

	public void setTouchTime(long touchTime) {
		this.touchTime = touchTime;
	}

	

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = java.lang.Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = java.lang.Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (touchTime ^ (touchTime >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OLDTimedPoint2D other = (OLDTimedPoint2D) obj;
		if (java.lang.Double.doubleToLongBits(latitude) != java.lang.Double.doubleToLongBits(other.latitude))
			return false;
		if (java.lang.Double.doubleToLongBits(longitude) != java.lang.Double.doubleToLongBits(other.longitude))
			return false;
		if (touchTime != other.touchTime)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TimedPoint2D [latitude=" + latitude + ", longitude=" + longitude + ", touchTime=" + touchTime + "]";
	}
	
	

}