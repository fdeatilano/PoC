package org.faf.persistence.entities;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.faf.config.AppConfiguration;
import org.faf.config.AppConfiguration.CheckinsFields;
import org.faf.persistence.PersistenceEntity;

public class CheckIn implements PersistenceEntity{

	private Integer id;
	private User user;
	private Place place;
	private Timestamp checkTime;
	private String device;
	private Double longitude;
	private Double latitude;

	public CheckIn(){}
	
	public CheckIn(User user, Place place, Double longitude, Double latitude, String device) {
		this.user=user;
		this.place=place;
		this.longitude=longitude;
		this.latitude=latitude;
		this.device=device;
	}

	public Integer getId() {
		return this.id;
	}

	public User getUser() {
		return this.user;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public String getDevice() {
		return this.device;
	}

	public void setUser(User user) {
		this.user=user;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setId(Integer identity) {
		this.id=identity;
	}
	
	public Timestamp getCheckTime() {
		return checkTime;
	}
	
	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	@Override
	public String getTableName() {
		return AppConfiguration.Tables.CHECKINS.name();
	}

	@Override
	public LinkedHashMap<String, Object> getFieldsAndValues() {
		LinkedHashMap<String, Object> fieldsValues = new LinkedHashMap<String,Object>();
		fieldsValues.put(CheckinsFields.USER_ID.name(), (user==null)?null:user.getId());
		fieldsValues.put(CheckinsFields.PLACE_ID.name(), (place==null)?null:place.getId());
		fieldsValues.put(CheckinsFields.LONGITUDE.name(), longitude);
		fieldsValues.put(CheckinsFields.LATITUDE.name(), latitude);
		fieldsValues.put(CheckinsFields.DEVICE.name(), device);
		return fieldsValues;
	}
	
	@Override
	public void setValues(LinkedHashMap<String, Object> values) {
		for (String field : values.keySet()) {
			Object currentField = values.get(field);
			if(field.equals(CheckinsFields.ID.name())){
				if (currentField instanceof String) {
					id=Integer.valueOf((String) currentField);
				}else if(currentField instanceof Integer) {
					id=(Integer)currentField;
				}
			} else if(field.equals(CheckinsFields.USER_ID.name())){
				if(user==null){
					user=new User();
				}
				if (currentField instanceof String) {
					user.setId(Integer.valueOf((String) currentField));
				}else if(currentField instanceof Integer) {
					user.setId((Integer)currentField);
				}
			} else if(field.equals(CheckinsFields.PLACE_ID.name())){
				if(place==null){
					place=new Place();
				} 
				if (currentField instanceof String) {
					place.setId(Integer.valueOf((String) currentField));
				}else if(currentField instanceof Integer) {
					place.setId((Integer)currentField);
				}
			}else if(field.equals(CheckinsFields.CHECK_TIME.name())){
				checkTime=(Timestamp)currentField;
			} else if(field.equals(CheckinsFields.LONGITUDE.name())){
				if (currentField instanceof String) {
					longitude=Double.valueOf((String) currentField);
				}else if(currentField instanceof Double) {
					longitude=(Double)currentField;					
				}
			} else if(field.equals(CheckinsFields.LATITUDE.name())){
				if (currentField instanceof String) {
					latitude=Double.valueOf((String) currentField);
				}else if(currentField instanceof Double) {
					latitude=(Double)currentField;					
				}
			} else if(field.equals(CheckinsFields.DEVICE.name())){
				device=(String)currentField;
			}
		}
	}
	
	@Override
	public LinkedHashMap<String, Class<?>> getFieldsAndTypes() {
		LinkedHashMap<String, Class<?>> fieldsTypes = new LinkedHashMap<String,Class<?>>();
		fieldsTypes.put(CheckinsFields.ID.name(), Integer.class);
		fieldsTypes.put(CheckinsFields.USER_ID.name(), Integer.class);
		fieldsTypes.put(CheckinsFields.PLACE_ID.name(), Integer.class);
		fieldsTypes.put(CheckinsFields.CHECK_TIME.name(), Timestamp.class);
		fieldsTypes.put(CheckinsFields.LONGITUDE.name(), Double.class);
		fieldsTypes.put(CheckinsFields.LATITUDE.name(), Double.class);
		fieldsTypes.put(CheckinsFields.DEVICE.name(), String.class);
		return fieldsTypes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((checkTime == null) ? 0 : checkTime.hashCode());
		result = prime * result + ((device == null) ? 0 : device.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((place == null) ? 0 : place.hashCode());
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
		if (checkTime == null) {
			if (other.checkTime != null)
				return false;
		} else if (!checkTime.equals(other.checkTime))
			return false;
		if (device == null) {
			if (other.device != null)
				return false;
		} else if (!device.equals(other.device))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (place == null) {
			if (other.place != null)
				return false;
		} else if (other.place==null) {
			return false;
		} else if (place.getId()==null) {
			if (other.place.getId()!=null) 
				return false;
		} else if (!place.getId().equals(other.place.getId()))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (other.user==null) {
			return false;
		} else if (user.getId()==null) {
			if (other.user.getId()!=null) 
				return false;
		} else if (!user.getId().equals(other.user.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CheckIn [id=" + id + ", user=" + ((user==null)?null:user.getId()) + ", place=" + ((place==null)?null:place.getId())
				+ ", checkTime=" + checkTime + ", device=" + device
				+ ", longitude=" + longitude + ", latitude=" + latitude + "]";
	}

	

}
