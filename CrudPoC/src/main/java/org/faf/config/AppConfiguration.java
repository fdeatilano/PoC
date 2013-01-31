package org.faf.config;

public class AppConfiguration {

	public static final String DB_URI = "jdbc:hsqldb:mem:CrudPoCDb";
	public static final String DB_USER = "walkin";
	public static final String DB_PASSWORD = "walkin";
	public static final Integer INITIAL_PARAM_INDEX = 0;
	public static final String DB_IDENTIFIER_FIELD = "id";

	public interface Fields{
		public abstract String getName();
		public abstract Fields[] getValues();
	};
	
	public static enum Tables{
		USERS("users"),PLACES("places"),CHECKINS("checkins");
	
		private String name;
		private Tables(String name){
			this.name=name;
		}
	};
	
	public static enum UsersFields implements Fields{
		ID(DB_IDENTIFIER_FIELD),LOGIN("login"),PASSWORD("password"),ROLE("role");
		
		private String name;
		UsersFields(String name){
			this.name=name;
		}
		
		@Override
		public String getName() {
			return this.name();
		}
		@Override
		public Fields[] getValues() {
			return this.values();
		}
	};
	
	public static enum CheckinsFields{
		ID(DB_IDENTIFIER_FIELD),USER_ID("user_id"),PLACE_ID("place_id"),CHECK_TIME("check_time"),LONGITUDE("longitude"),LATITUDE("latitude"),DEVICE("device");
	
		private String name;
		CheckinsFields(String name){
			this.name=name;
		}
	};
	
	public static enum PlacesFields{
		ID(DB_IDENTIFIER_FIELD),LONGITUDE("longitude"),LATITUDE("latitude"),ADDRESS("address");
	
		private String name;
		PlacesFields(String name){
			this.name=name;
		}
	};
	
	public static final String ROLE_ADMIN = "Administrator";
	public static final String ROLE_USER = "User";
	public static final String AUTHENTICATE_URL = "authenticate";
	
	public static enum Actions{
		PUT("PUT"),GET("GET"),POST("POST"),DELETE("DELETE");
		
		private String name;
		Actions(String name){
			this.name=name;
		}
	}
}
