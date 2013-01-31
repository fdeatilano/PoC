package org.faf.controller;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.faf.config.AppConfiguration;
import org.faf.persistence.PersistenceEntity;
import org.faf.persistence.PersistenceManager;
import org.faf.persistence.entities.CheckIn;
import org.faf.persistence.entities.Place;
import org.faf.persistence.entities.User;
import org.faf.view.View;
import org.faf.view.views.Delete;
import org.faf.view.views.Get;
import org.faf.view.views.Put;
import org.faf.view.views.Role;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CrudControllerTest {
	
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
		Role expectedRole = new Role(null);
		Assert.assertEquals(expectedRole.render(), _crudController.authenticate(null, null).render());
	}
	
	@Test
	public void testAdminAuthentication() throws Exception {
		String login = "admin";
		String password = "admin";
		PersistenceEntity inputUser = new User(login,password,AppConfiguration.ROLE_ADMIN);
		_crudController.put(inputUser);
		Role role = (Role) _crudController.authenticate(login, password);
		Assert.assertEquals(true, role.isAdmin());
	}
	
	@Test
	public void testPutUser() throws Exception {
		PersistenceEntity inputUser = new User("admin","admin",AppConfiguration.ROLE_ADMIN);
		_crudController.put(inputUser);
		View actualView = (Get) _crudController.get(inputUser);
		List<PersistenceEntity> entities = new LinkedList<PersistenceEntity>();
		entities.add(inputUser);
		View getViewExpected = new Get(entities);
		Assert.assertEquals(getViewExpected.render(), actualView.render());
	}
	
	@Test
	public void testGetCheckInsInPlace() throws Exception {
		PersistenceEntity preferedPlace = new Place(4.0,5.0,"Prefered place");
		_crudController.put(preferedPlace);
		PersistenceEntity exclusivePlace = new Place(1.0,2.0,"Exclusive place");
		_crudController.put(exclusivePlace);
		PersistenceEntity tom = new User("tom","password",AppConfiguration.ROLE_USER);
		_crudController.put(tom);
		PersistenceEntity ann = new User("ann","password",AppConfiguration.ROLE_USER);
		_crudController.put(ann);
		
		PersistenceEntity checkInTom = new CheckIn((User)tom,(Place)preferedPlace,4.1,4.9,"Tom's phone");
		_crudController.put(checkInTom);
		PersistenceEntity checkInAnnPrefered = new CheckIn((User)ann,(Place)preferedPlace,4.0,4.9,"Ann's tablet");
		_crudController.put(checkInAnnPrefered);
		PersistenceEntity checkInAnnExclusive = new CheckIn((User)ann,(Place)exclusivePlace,1.0,2.1,"Ann's phone");
		_crudController.put(checkInAnnExclusive);
		
		PersistenceEntity checkInSearchCriteria = new CheckIn(null,(Place)preferedPlace,null,null,null);
		View actualView = (Get) _crudController.get(checkInSearchCriteria);
		List<PersistenceEntity> entities = new LinkedList<PersistenceEntity>();
		entities.add(_pm.read(CheckIn.class, checkInTom.getId()));
		entities.add(_pm.read(CheckIn.class, checkInAnnPrefered.getId()));
		View getViewExpected = new Get(entities);
		Assert.assertEquals(getViewExpected.render(), actualView.render());
	}
	
	@Test
	public void testPostPlace() throws Exception {
		PersistenceEntity inputPlace = new Place(4.0,5.0,"Address");
		_crudController.put(inputPlace);
		PersistenceEntity updatePlace = new Place(7.0,1.0,"UpdatedAddress");
		updatePlace.setId(inputPlace.getId());
		_crudController.post(updatePlace);
		PersistenceEntity placeById = new Place();
		placeById.setId(inputPlace.getId());
		View actualView = (Get) _crudController.get(placeById);
		List<PersistenceEntity> entities = new LinkedList<PersistenceEntity>();
		entities.add(updatePlace);
		View getViewExpected = new Get(entities);
		Assert.assertEquals(getViewExpected.render(), actualView.render());
	}
	
	@Test
	public void testDeleteCheckIn() throws Exception {
		PersistenceEntity place = new Place(4.0,5.0,"Address");
		_crudController.put(place);
		PersistenceEntity user = new User("login","password",AppConfiguration.ROLE_ADMIN);
		_crudController.put(user);
		PersistenceEntity checkIn = new CheckIn((User)user,(Place)place,4.0,5.0,"Device");
		_crudController.put(checkIn);
		View actualDeleteView = _crudController.delete(checkIn);
		View deleteViewExpected = new Delete(checkIn);
		Assert.assertEquals(deleteViewExpected.render(), actualDeleteView.render());
		
		View actualGetView = (Get) _crudController.get(checkIn);
		LinkedList<PersistenceEntity> emptyList = new LinkedList<PersistenceEntity>();
		View getViewExpected = new Get(emptyList);
		Assert.assertEquals(getViewExpected.render(), actualGetView.render());
	}
}
