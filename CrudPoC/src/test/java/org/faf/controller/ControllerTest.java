package org.faf.controller;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.faf.persistence.PersistenceEntity;
import org.faf.persistence.PersistenceManager;
import org.faf.persistence.config.DbConfiguration;
import org.faf.persistence.entities.User;
import org.faf.view.views.GetView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ControllerTest {
	
	private CrudController _crudController;
	private PersistenceManager _pm;

	@Before
	public void before() throws SQLException {
		_crudController = new CrudController();
		_pm = new PersistenceManager();
		_pm.initializeDB();
	}
	
	@Test
	public void testWrongAuthentication() throws Exception {
		Assert.assertEquals(null, _crudController.authenticate(null, null));
	}
	
	@Test
	public void testAdminAuthentication() throws Exception {
		String login = "admin";
		String password = "admin";
		User inputUser = new User(login,password,DbConfiguration.ROLE_ADMIN);
		_crudController.put(inputUser);
		Assert.assertEquals(true, _crudController.authenticate(login, password).isAdmin());
	}
	
	@Test
	public void testPutUser() throws Exception {
		User inputUser = new User("admin","admin",DbConfiguration.ROLE_ADMIN);
		_crudController.put(inputUser);
		GetView actualView = (GetView) _crudController.get(inputUser);
		List<PersistenceEntity> entities = new LinkedList<PersistenceEntity>();
		entities.add(inputUser);
		GetView getViewExpected = new GetView(entities);
		Assert.assertEquals(getViewExpected.render(), actualView.render());
	}
}
