package org.faf.persistence.util;

public class DbConstants {

	public static final String DB_URI = "jdbc:hsqldb:mem:CrudPoCDb";
	public static final String DB_USER = "walkin";
	public static final String DB_PASSWORD = "walkin";
	public static final Integer INITIAL_PARAM_INDEX = 0;
	public static final String DB_IDENTIFIER_FIELD = "id";
	
	public static final String USERS_TABLE = "users";
	public static enum UsersFields{
		ID(DB_IDENTIFIER_FIELD),LOGIN("login"),PASSWORD("password");
	
		private String name;
		UsersFields(String name){
			this.name=name;
		}
	};
	
	public static final String CHECKINS_TABLE = "checkins";
	public static enum CheckinsFields{
		ID(DB_IDENTIFIER_FIELD),USER_ID("user_id"),CHECK_TIME("check_time"),LONGITUDE("longitude"),LATITUDE("latitude"),DEVICE("device");
	
		private String name;
		CheckinsFields(String name){
			this.name=name;
		}
	};
}
