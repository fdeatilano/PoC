package org.faf.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.faf.config.AppConfiguration;
import org.faf.config.AppConfiguration.CheckinsFields;
import org.faf.config.AppConfiguration.PlacesFields;
import org.faf.config.AppConfiguration.UsersFields;
import org.faf.persistence.PersistenceEntity;
import org.faf.persistence.entities.CheckIn;
import org.faf.persistence.entities.Place;
import org.faf.persistence.entities.User;
import org.faf.security.PermissionsManager;
import org.faf.view.View;
import org.faf.view.views.Role;

public class FrontController extends HttpServlet{
	
	private PermissionsManager _permManager = new PermissionsManager();
	private String ACCESS_NOT_ALLOWED = "Access not allowed";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		if(_permManager.allows(req,req.getMethod(),getPredicate(req))){
			super.service(req, resp);
		}else{
			PrintWriter out = resp.getWriter();
			out.println(ACCESS_NOT_ALLOWED);
		}
	}
	
	public void doPut(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		CrudController controller = new CrudController();
		PersistenceEntity entity = buildEntity(request);
		if(entity!=null){
			View view = controller.put(entity);
			PrintWriter out = response.getWriter();
			out.println(view.render());
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		CrudController controller = new CrudController();
		PrintWriter out = response.getWriter();
		if(getPredicate(request).equals(AppConfiguration.AUTHENTICATE_URL)){
			String login = request.getParameter(AppConfiguration.UsersFields.LOGIN.name().toLowerCase());
			String password = request.getParameter(AppConfiguration.UsersFields.PASSWORD.name().toLowerCase());
			Role role = (Role) controller.authenticate(login, password);
			if(role!=null){
				HttpSession session = request.getSession();
				session.setAttribute(AppConfiguration.UsersFields.ROLE.name().toLowerCase(), role);
				out.println(role.render());
			}
		}else{
			PersistenceEntity entity = buildEntity(request);
			if(entity!=null){
				View view = controller.get(entity);
				out.println(view.render());
			}
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		CrudController controller = new CrudController();
		PersistenceEntity entity = buildEntity(request);
		if(entity!=null){
			View view = controller.post(entity);
			PrintWriter out = response.getWriter();
			out.println(view.render());
		}
	}

	public void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		CrudController controller = new CrudController();
		PersistenceEntity entity = buildEntity(request);
		if(entity!=null){
			View view = controller.delete(entity);
			PrintWriter out = response.getWriter();
			out.println(view.render());
		}
	}
	
	private PersistenceEntity buildEntity(HttpServletRequest request) {
		PersistenceEntity entity = null;
		if(getPredicate(request).equals(AppConfiguration.Tables.USERS.name().toLowerCase())){
			entity = new User();
			for (UsersFields field : AppConfiguration.UsersFields.values()) {
				Map<String,Object> values = new LinkedHashMap<String,Object>();
				values.put(field.name(), request.getParameter(field.name()));
			}
		} else if(getPredicate(request).equals(AppConfiguration.Tables.PLACES.name().toLowerCase())){
			entity = new Place();
			for (PlacesFields field : AppConfiguration.PlacesFields.values()) {
				Map<String,Object> values = new LinkedHashMap<String,Object>();
				values.put(field.name(), request.getParameter(field.name()));
			}
		} else if(getPredicate(request).equals(AppConfiguration.Tables.CHECKINS.name().toLowerCase())){
			entity = new CheckIn();
			for (CheckinsFields field : AppConfiguration.CheckinsFields.values()) {
				Map<String,Object> values = new LinkedHashMap<String,Object>();
				values.put(field.name(), request.getParameter(field.name()));
			}
		}
		return entity;
	}

	private String getPredicate(HttpServletRequest request) {
		return request.getPathInfo().replaceFirst("/", "").toLowerCase();
	}
}
