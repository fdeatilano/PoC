package org.faf.persistence;

import java.util.LinkedHashMap;

public interface PersistenceEntity {

	public String getTableName();
	public LinkedHashMap<String,Object> getFieldsAndValues();
	public void setValues(LinkedHashMap<String,Object> values);
	public LinkedHashMap<String, Class<?>> getFieldsAndTypes();
	public void setId(Integer id);
	public Integer getId();
}
