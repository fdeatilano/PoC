package org.faf.persistence.db;

import org.faf.persistence.util.Device;
import org.faf.persistence.util.GeoLocation;

public class CheckIn {

	private Integer id;
	private User user;
	private GeoLocation geolocation;
	private Device device;

	public CheckIn(User user, GeoLocation geolocation, Device device) {
		this.user=user;
		this.geolocation=geolocation;
		this.device=device;
	}

	public Integer getId() {
		return this.id;
	}

	public Integer getUserId() {
		return this.user.getId();
	}

	public Double getLongitude() {
		return this.geolocation.getLongitude();
	}

	public Double getLatitude() {
		return this.geolocation.getLatitude();
	}

	public String getDeviceName() {
		return this.device.getName();
	}

	public void setId(Integer identity) {
		this.id=identity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((device == null) ? 0 : device.hashCode());
		result = prime * result
				+ ((geolocation == null) ? 0 : geolocation.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		CheckIn other = (CheckIn) obj;
		if (device == null) {
			if (other.device != null)
				return false;
		} else if (!device.equals(other.device))
			return false;
		if (geolocation == null) {
			if (other.geolocation != null)
				return false;
		} else if (!geolocation.equals(other.geolocation))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	

}
