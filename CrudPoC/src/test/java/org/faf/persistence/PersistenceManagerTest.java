package org.faf.persistence;


import java.sql.SQLException;

import org.faf.persistence.db.CheckIn;
import org.faf.persistence.db.User;
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
	
	@Test
	public void testCreateUser() throws Exception {
		User user = new User("login","password");
		_pm.create(user);
		Assert.assertEquals(new Integer(1), user.getId());
	}
	
	@Test
	public void testCreateCheckIn() throws Exception {
		User user = new User("login","password");
		_pm.create(user);
		CheckIn checkIn = new CheckIn(user,1.0,2.0,"Mobile");
		_pm.create(checkIn);
		Assert.assertEquals(new Integer(1), checkIn.getId());
	}
		
	@Test
	public void testReadUser() throws Exception {
		User user = new User("login","password");
		_pm.create(user);
		User storedUser = (User)_pm.read(User.class, user.getId());
		Assert.assertEquals(user,storedUser);
	}
	
	@Test
	public void testReadCheckIn() throws Exception {
		User user = new User("login","password");
		_pm.create(user);
		CheckIn checkIn = new CheckIn(user,1.0,2.0,"Mobile");
		checkIn = (CheckIn)_pm.create(checkIn);
		CheckIn storedCheckIn = (CheckIn)_pm.read(CheckIn.class, checkIn.getId());
		Assert.assertEquals(checkIn,storedCheckIn);
	}
	
	@Test
	public void testUpdateUser() throws Exception {
		User user = new User("login","password");
		_pm.create(user);
		user.setLogin("updatedLogin");
		user.setPassword("updatedPassword");
		_pm.update(user);
		User updatedUser = (User)_pm.read(User.class, user.getId());
		Assert.assertEquals(user,updatedUser);
	}
	
	@Test
	public void testUpdateCheckin() throws Exception {
		User user = new User("login","password");
		_pm.create(user);
		CheckIn checkIn = new CheckIn(user,1.0,2.0,"Mobile");
		checkIn = (CheckIn)_pm.create(checkIn);
		checkIn.setLatitude(4.0);
		checkIn.setLongitude(6.0);
		checkIn.setDevice("updatedMobile");
		_pm.update(checkIn);
		CheckIn updatedCheckIn = (CheckIn)_pm.read(CheckIn.class, checkIn.getId());
		Assert.assertEquals(checkIn,updatedCheckIn);
	}
	
	@Test
	public void testDeleteUser() throws Exception {
		User user = new User("login","password");
		_pm.create(user);
		User storedUser = (User)_pm.read(User.class, user.getId());
		Assert.assertEquals(user,storedUser);
		_pm.delete(User.class, user.getId());
		storedUser = (User)_pm.read(User.class, user.getId());
		Assert.assertEquals(null,storedUser);
	}
	
	@Test
	public void testDeleteCheckIn() throws Exception {
		User user = new User("login","password");
		_pm.create(user);
		CheckIn checkIn = new CheckIn(user,1.0,2.0,"Mobile");
		checkIn = (CheckIn)_pm.create(checkIn);
		CheckIn storedCheckIn = (CheckIn)_pm.read(CheckIn.class, checkIn.getId());
		Assert.assertEquals(checkIn,storedCheckIn);
		_pm.delete(CheckIn.class, checkIn.getId());
		storedCheckIn = (CheckIn)_pm.read(CheckIn.class, checkIn.getId());
		Assert.assertEquals(null,storedCheckIn);
	}
}