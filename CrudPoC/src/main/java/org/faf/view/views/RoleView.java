package org.faf.view.views;

import org.faf.persistence.config.DbConfiguration;
import org.faf.view.View;


public class RoleView implements View{

	private String role;

	public RoleView(String role) {
		this.role=role;
	}

	public Boolean isAdmin() {
		if(role.equals(DbConfiguration.ROLE_ADMIN)){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean isUser() {
		if(role.equals(DbConfiguration.ROLE_USER)){
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
