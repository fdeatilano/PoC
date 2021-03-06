package org.faf.persistence.entities;

import java.util.LinkedHashMap;

import org.faf.config.AppConfiguration;
import org.faf.config.AppConfiguration.PlacesFields;
import org.faf.persistence.PersistenceEntity;


public class Place implements PersistenceEntity{

	private Integer id;
	private Double longitude;
	private Double latitude;
	private String address;

	public Place(){}
	
	public Place(Double longitude, Double latitude, String address) {
		this.longitude=longitude;
		this.latitude=latitude;
		this.address=address;
	}

	public Integer getId() {
		return this.id;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public Double getLatitude() {
		return this.latitude;
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
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String getTableName() {
		return AppConfiguration.Tables.PLACES.name();
	}

	@Override
	public LinkedHashMap<String, Object> getFieldsAndValues() {
		LinkedHashMap<String, Object> fieldsValues = new LinkedHashMap<String,Object>();
		fieldsValues.put(PlacesFields.LONGITUDE.name(), longitude);
		fieldsValues.put(PlacesFields.LATITUDE.name(), latitude);
		fieldsValues.put(PlacesFields.ADDRESS.name(), address);
		return fieldsValues;
	}
	
	@Override
	public void setValues(LinkedHashMap<String, Object> values) {
		for (String field : values.keySet()) {
			Object currentField = values.get(field);
			if(field.equals(PlacesFields.ID.name())){
				if (currentField instanceof String) {
					id=Integer.valueOf((String) currentField);
				}else if(currentField instanceof Integer) {
					id=(Integer)currentField;
				}
			} else if(field.equals(PlacesFields.LONGITUDE.name())){
				if (currentField instanceof String) {
					longitude=Double.valueOf((String) currentField);
				}else if(currentField instanceof Double) {
					longitude=(Double)currentField;					
				}
			} else if(field.equals(PlacesFields.LATITUDE.name())){
				if (currentField instanceof String) {
					latitude=Double.valueOf((String) currentField);
				}else if(currentField instanceof Double) {
					latitude=(Double)currentField;					
				}
			} else if(field.equals(PlacesFields.ADDRESS.name())){
				address=(String)currentField;
			}
		}
	}
	
	@Override
	public LinkedHashMap<String, Class<?>> getFieldsAndTypes() {
		LinkedHashMap<String, Class<?>> fieldsTypes = new LinkedHashMap<String,Class<?>>();
		fieldsTypes.put(PlacesFields.ID.name(), Integer.class);
		fieldsTypes.put(PlacesFields.LONGITUDE.name(), Double.class);
		fieldsTypes.put(PlacesFields.LATITUDE.name(), Double.class);
		fieldsTypes.put(PlacesFields.ADDRESS.name(), String.class);
		return fieldsTypes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
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
		Place other = (Place) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
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
		return true;
	}

	@Override
	public String toString() {
		return "Place [id=" + id + ", longitude=" + longitude + ", latitude="
				+ latitude + ", address=" + address + "]";
	}

}
