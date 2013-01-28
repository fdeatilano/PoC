package org.faf.persistence.db;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.faf.persistence.PersistenceEntity;
import org.faf.persistence.util.DbConstants;
import org.faf.persistence.util.DbConstants.CheckinsFields;

public class CheckIn implements PersistenceEntity{

	private Integer id;
	private User user;
	private Timestamp checkTime;
	private String device;
	private Double longitude;
	private Double latitude;

	public CheckIn(){}
	
	public CheckIn(User user, Double longitude, Double latitude, String device) {
		this.user=user;
		this.longitude=longitude;
		this.latitude=latitude;
		this.device=device;
	}

	public Integer getId() {
		return this.id;
	}

	public Integer getUserId() {
		return this.user.getId();
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
	

	public void setUserId(Integer userId) {
		this.user.setId(userId);
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

	@Override
	public String getTableName() {
		return DbConstants.CHECKINS_TABLE;
	}

	@Override
	public LinkedHashMap<String, Object> getFieldsAndValues() {
		LinkedHashMap<String, Object> fieldsValues = new LinkedHashMap<String,Object>();
		fieldsValues.put(CheckinsFields.USER_ID.name(), user.getId());
		fieldsValues.put(CheckinsFields.LONGITUDE.name(), longitude);
		fieldsValues.put(CheckinsFields.LATITUDE.name(), latitude);
		fieldsValues.put(CheckinsFields.DEVICE.name(), device);
		return fieldsValues;
	}
	
	@Override
	public void setValues(LinkedHashMap<String, Object> values) {
		for (String field : values.keySet()) {
			if(field.equals(CheckinsFields.ID.name())){
				id=(Integer)values.get(field);
			} else if(field.equals(CheckinsFields.USER_ID.name())){
				if(user==null){
					user=new User();
				}
				user.setId((Integer)values.get(field));
			} else if(field.equals(CheckinsFields.CHECK_TIME.name())){
				checkTime=(Timestamp)values.get(field);
			} else if(field.equals(CheckinsFields.LONGITUDE.name())){
				longitude=(Double)values.get(field);
			} else if(field.equals(CheckinsFields.LATITUDE.name())){
				latitude=(Double)values.get(field);
			} else if(field.equals(CheckinsFields.DEVICE.name())){
				device=(String)values.get(field);
			}
		}
	}
	
	@Override
	public LinkedHashMap<String, Class<?>> getFieldsAndTypes() {
		LinkedHashMap<String, Class<?>> fieldsTypes = new LinkedHashMap<String,Class<?>>();
		fieldsTypes.put(CheckinsFields.ID.name(), Integer.class);
		fieldsTypes.put(CheckinsFields.USER_ID.name(), Integer.class);
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
		result = prime * result + ((device == null) ? 0 : device.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
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
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.getId().equals(other.user.getId()))
			return false;
		return true;
	}
	
}
