package com.bonvoyage.domain;

import java.io.Serializable;
import java.util.Date;

public class SolDetGridRow implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = -2691648503842533895L;
	private String waitString;
	 private String depString;
	 private String arrString;
	 private String segDuration; 
	 private String segLenString;
	 
	public SolDetGridRow(String waitString, String depString, String arrString, String segDuration,
			String segLenString) {
		super();
		this.waitString = waitString;
		this.depString = depString;
		this.arrString = arrString;
		this.segDuration = segDuration;
		this.segLenString = segLenString;
	}

	public String getWaitString() {
		return waitString;
	}

	public void setWaitString(String waitString) {
		this.waitString = waitString;
	}

	public String getDepString() {
		return depString;
	}

	public void setDepString(String depString) {
		this.depString = depString;
	}

	public String getArrString() {
		return arrString;
	}

	public void setArrString(String arrString) {
		this.arrString = arrString;
	}

	public String getSegDuration() {
		return segDuration;
	}

	public void setSegDuration(String segDuration) {
		this.segDuration = segDuration;
	}

	public String getSegLenString() {
		return segLenString;
	}

	public void setSegLenString(String segLenString) {
		this.segLenString = segLenString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrString == null) ? 0 : arrString.hashCode());
		result = prime * result + ((depString == null) ? 0 : depString.hashCode());
		result = prime * result + ((segDuration == null) ? 0 : segDuration.hashCode());
		result = prime * result + ((segLenString == null) ? 0 : segLenString.hashCode());
		result = prime * result + ((waitString == null) ? 0 : waitString.hashCode());
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
		SolDetGridRow other = (SolDetGridRow) obj;
		if (arrString == null) {
			if (other.arrString != null)
				return false;
		} else if (!arrString.equals(other.arrString))
			return false;
		if (depString == null) {
			if (other.depString != null)
				return false;
		} else if (!depString.equals(other.depString))
			return false;
		if (segDuration == null) {
			if (other.segDuration != null)
				return false;
		} else if (!segDuration.equals(other.segDuration))
			return false;
		if (segLenString == null) {
			if (other.segLenString != null)
				return false;
		} else if (!segLenString.equals(other.segLenString))
			return false;
		if (waitString == null) {
			if (other.waitString != null)
				return false;
		} else if (!waitString.equals(other.waitString))
			return false;
		return true;
	}
	 
	 

}
