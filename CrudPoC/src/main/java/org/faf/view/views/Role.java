package org.faf.view.views;

import org.faf.config.AppConfiguration;
import org.faf.view.View;


public class Role implements View{

	private String role;

	public Role(String role) {
		this.role=role;
	}

	public Boolean isAdmin() {
		if(role.equals(AppConfiguration.ROLE_ADMIN)){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean isUser() {
		if(role.equals(AppConfiguration.ROLE_USER)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String render() {
		if(role==null){
			return "Ups! There was an error, maybe you introduced an invalid user or password.\n Just try again!";
		}else{
			return "You are logged in now as "+role;
		}
	}

}
