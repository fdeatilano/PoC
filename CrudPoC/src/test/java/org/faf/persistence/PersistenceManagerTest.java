package org.faf.persistence;


import java.sql.SQLException;

import org.faf.persistence.config.DbConfiguration;
import org.faf.persistence.config.DbConfiguration.UsersFields;
import org.faf.persistence.entities.CheckIn;
import org.faf.persistence.entities.Place;
import org.faf.persistence.entities.User;
import org.faf.persistence.exceptions.UnableToCreateEntityException;
import org.faf.persistence.exceptions.UnableToDeleteEntityException;
import org.faf.persistence.exceptions.UnableToRetrieveEntityException;
import org.faf.persistence.exceptions.UnableToUpdateEntityException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PersistenceManagerTest {

	private PersistenceManager _pm;


	@Before
	public void before() throws SQLException {
		_pm = new PersistenceManager();
		_pm.initializeDB();
	}
	
	@Test(expected=UnableToCreateEntityException.class)
	public void testCreateWithNullId() throws Exception {
		_pm.create(new User());
	}
	
	@Test
	public void testCreateUser() throws Exception {
		User user = new User("login",null,DbConfiguration.ROLE_ADMIN);
		_pm.create(user);
		Assert.assertEquals(new Integer(1), user.getId());
	}
	
	@Test
	public void testCreatePlace() throws Exception {
		Place place = new Place(1.0,2.0,"address");
		_pm.create(place);
		Assert.assertEquals(new Integer(1), place.getId());
	}
	
	@Test
	public void testCreateCheckIn() throws Exception {
		User user = new User(null,"password",null);
		_pm.create(user);
		Place place = new Place(1.0,2.0,"address");
		_pm.create(place);
		CheckIn checkIn = new CheckIn(user,place,1.0,2.0,"Mobile");
		_pm.create(checkIn);
		Assert.assertEquals(new Integer(1), checkIn.getId());
	}
	
	@Test(expected=UnableToRetrieveEntityException.class)
	public void testReadWithNullId() throws Exception {
		_pm.read(User.class,(Integer)null);
	}
		
	@Test
	public void testReadUser() throws Exception {
		User user = new User("login","password",DbConfiguration.ROLE_USER);
		_pm.create(user);
		User storedUser = (User)_pm.read(User.class, user.getId());
		Assert.assertEquals(user,storedUser);
	}
	
	@Test
	public void testReadUserWithLoginAndPassword() throws Exception {
		String login = "login";
		String password = "password";
		User user = new User(login,password,DbConfiguration.ROLE_USER);
		_pm.create(user);
		WhereClause where = new WhereClause();
		where.addCriteria(UsersFields.LOGIN.name(), login);
		where.addCriteria(UsersFields.PASSWORD.name(), password);
		User storedUser = (User)_pm.read(User.class, where).get(0);
		Assert.assertEquals(user,storedUser);
	}
	
	@Test
	public void testReadPlace() throws Exception {
		Place place = new Place(1.0,2.0,"address");
		_pm.create(place);
		Place storedPlace = (Place)_pm.read(Place.class, place.getId());
		Assert.assertEquals(place,storedPlace);
	}
	
	@Test
	public void testReadCheckIn() throws Exception {
		User user = new User("login","password",null);
		_pm.create(user);
		Place place = new Place(1.0,2.0,"address");
		_pm.create(place);
		CheckIn checkIn = new CheckIn(user,place,1.0,2.0,"Mobile");
		checkIn = (CheckIn)_pm.create(checkIn);
		CheckIn storedCheckIn = (CheckIn)_pm.read(CheckIn.class, checkIn.getId());
		Assert.assertEquals(checkIn,storedCheckIn);
	}
	
	@Test(expected=UnableToUpdateEntityException.class)
	public void testUpdateWithNullId() throws Exception {
		_pm.update(new User());
	}
	
	@Test
	public void testUpdateUser() throws Exception {
		User user = new User("login","password",null);
		_pm.create(user);
		user.setLogin("updatedLogin");
		user.setPassword("updatedPassword");
		_pm.update(user);
		User updatedUser = (User)_pm.read(User.class, user.getId());
		Assert.assertEquals(user,updatedUser);
	}
	
	@Test
	public void testUpdatePlace() throws Exception {
		Place place = new Place(1.0,2.0,"address");
		_pm.create(place);
		place.setLatitude(3.0);
		place.setLongitude(10.0);
		place.setAddress("updatedAddress");
		_pm.update(place);
		Place updatedPlace = (Place)_pm.read(Place.class, place.getId());
		Assert.assertEquals(place,updatedPlace);
	}
	
	@Test
	public void testUpdateCheckin() throws Exception {
		User user = new User("login","password",null);
		_pm.create(user);
		Place place = new Place(1.0,2.0,"address");
		_pm.create(place);
		CheckIn checkIn = new CheckIn(user,place,1.0,2.0,"Mobile");
		checkIn = (CheckIn)_pm.create(checkIn);
		checkIn.setLatitude(4.0);
		checkIn.setLongitude(6.0);
		checkIn.setDevice("updatedMobile");
		_pm.update(checkIn);
		CheckIn updatedCheckIn = (CheckIn)_pm.read(CheckIn.class, checkIn.getId());
		Assert.assertEquals(checkIn,updatedCheckIn);
	}
	
	@Test(expected=UnableToDeleteEntityException.class)
	public void testDeleteWithNullId() throws Exception {
		_pm.delete(User.class, null);
	}
	
	@Test
	public void testDeleteUser() throws Exception {
		User user = new User("login","password",null);
		_pm.create(user);
		User storedUser = (User)_pm.read(User.class, user.getId());
		Assert.assertEquals(user,storedUser);
		_pm.delete(User.class, user.getId());
		storedUser = (User)_pm.read(User.class, user.getId());
		Assert.assertEquals(null,storedUser);
	}
	
	@Test
	public void testDeletePlace() throws Exception {
		Place place = new Place(1.0,2.0,"address");
		_pm.create(place);
		Place storedPlace = (Place)_pm.read(Place.class, place.getId());
		Assert.assertEquals(place,storedPlace);
		_pm.delete(Place.class, place.getId());
		storedPlace = (Place)_pm.read(Place.class, place.getId());
		Assert.assertEquals(null,storedPlace);
	}
	
	@Test
	public void testDeleteCheckIn() throws Exception {
		User user = new User("login","password",null);
		_pm.create(user);
		Place place = new Place(1.0,2.0,"address");
		_pm.create(place);
		CheckIn checkIn = new CheckIn(user,place,1.0,2.0,"Mobile");
		checkIn = (CheckIn)_pm.create(checkIn);
		CheckIn storedCheckIn = (CheckIn)_pm.read(CheckIn.class, checkIn.getId());
		Assert.assertEquals(checkIn,storedCheckIn);
		_pm.delete(CheckIn.class, checkIn.getId());
		storedCheckIn = (CheckIn)_pm.read(CheckIn.class, checkIn.getId());
		Assert.assertEquals(null,storedCheckIn);
	}
}