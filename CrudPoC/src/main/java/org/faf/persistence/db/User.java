package org.faf.persistence.db;

import java.util.LinkedHashMap;

import org.faf.persistence.PersistenceEntity;
import org.faf.persistence.util.DbConstants;
import org.faf.persistence.util.DbConstants.UsersFields;

public class User implements PersistenceEntity{

	private Integer id;
	private String login;
	private String password;

	public User(){}
	
	public User(String login, String password) {
		this.login=login;
		this.password=password;
	}

	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id=id;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getTableName() {
		return DbConstants.USERS_TABLE;
	}

	@Override
	public LinkedHashMap<String, Object> getFieldsAndValues() {
		LinkedHashMap<String, Object> fieldsValues = new LinkedHashMap<String,Object>();
		fieldsValues.put(UsersFields.LOGIN.name(), login);
		fieldsValues.put(UsersFields.PASSWORD.name(), password);
		return fieldsValues;
	}
	
	@Override
	public void setValues(LinkedHashMap<String, Object> values) {
		for (String field : values.keySet()) {
			if(field.equals(UsersFields.ID.name())){
				id=(Integer)values.get(field);
			} else if(field.equals(UsersFields.LOGIN.name())){
				login=(String)values.get(field);
			} else if(field.equals(UsersFields.PASSWORD.name())){
				password=(String)values.get(field);
			}
		}
	}
	
	@Override
	public LinkedHashMap<String, Class<?>> getFieldsAndTypes() {
		LinkedHashMap<String, Class<?>> fieldsTypes = new LinkedHashMap<String,Class<?>>();
		fieldsTypes.put(UsersFields.ID.name(), Integer.class);
		fieldsTypes.put(UsersFields.LOGIN.name(), String.class);
		fieldsTypes.put(UsersFields.PASSWORD.name(), String.class);
		return fieldsTypes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
