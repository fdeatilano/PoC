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

import org.faf.persistence.PersistenceEntity;
import org.faf.persistence.config.DbConfiguration;
import org.faf.persistence.config.DbConfiguration.UsersFields;
import org.faf.persistence.entities.CheckIn;
import org.faf.persistence.entities.Place;
import org.faf.persistence.entities.User;
import org.faf.view.View;
import org.faf.view.views.Role;

public class FrontController extends HttpServlet{

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
		out.println("Hola Get "+request.getPathInfo().replaceFirst("/", ""));
		if(request.getPathInfo().replaceFirst("/", "").equals(DbConfiguration.INITIALIZE_DB_URL)){
			controller.initialize();
			out.println("The application has been initialized.");
		}else if(request.getPathInfo().replaceFirst("/", "").equals(DbConfiguration.AUTHENTICATE_URL)){
			String login = request.getParameter(DbConfiguration.UsersFields.LOGIN.name().toLowerCase());
			String password = request.getParameter(DbConfiguration.UsersFields.PASSWORD.name().toLowerCase());
			out.println("login: "+login+" - password: "+password);
			out.println(controller.get(new User()).render());
			Role role = (Role) controller.authenticate(login, password);
			out.println(role);
			if(role!=null){
				HttpSession session = request.getSession();
				session.setAttribute(DbConfiguration.UsersFields.ROLE.name().toLowerCase(), role);
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
		if(request.getPathInfo().replaceFirst("/", "").equals(DbConfiguration.Tables.USERS.name().toLowerCase())){
			entity = new User();
			for (UsersFields field : DbConfiguration.UsersFields.values()) {
				Map<String,Object> values = new LinkedHashMap<String,Object>();
				values.put(field.name(), request.getParameter(field.name()));
			}
		} else if(request.getPathInfo().replaceFirst("/", "").equals(DbConfiguration.Tables.PLACES.name().toLowerCase())){
			entity = new Place();
			for (UsersFields field : DbConfiguration.UsersFields.values()) {
				Map<String,Object> values = new LinkedHashMap<String,Object>();
				values.put(field.name(), request.getParameter(field.name()));
			}
		} else if(request.getPathInfo().replaceFirst("/", "").equals(DbConfiguration.Tables.CHECKINS.name().toLowerCase())){
			entity = new CheckIn();
			for (UsersFields field : DbConfiguration.UsersFields.values()) {
				Map<String,Object> values = new LinkedHashMap<String,Object>();
				values.put(field.name(), request.getParameter(field.name()));
			}
		}
		return entity;
	}
}