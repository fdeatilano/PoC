package org.faf.security;

import static org.faf.config.AppConfiguration.Tables.CHECKINS;
import static org.faf.config.AppConfiguration.Tables.USERS;
import static org.faf.config.AppConfiguration.Tables.PLACES;
import java.awt.Desktop.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.faf.config.AppConfiguration;
import org.faf.config.AppConfiguration.Actions;
import org.faf.view.views.Role;

public class PermissionsManager {

	public boolean allows(Role role, String action,
			String predicate) {
		
		if(Actions.GET.name().equals(action)){
			if(AppConfiguration.AUTHENTICATE_URL.equals(predicate)){
				return true;
			}
		}
		if(role==null){
			return false;
		}else{
			if(role.isAdmin()){
				return true;
			}else if(role.isUser()){
				if(Actions.GET.equals(action)){
					return true;
				}else if(Actions.PUT.equals(action)){
					if(CHECKINS.equals(predicate)){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
	}

}
