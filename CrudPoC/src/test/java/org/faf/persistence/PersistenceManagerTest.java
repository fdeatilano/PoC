package org.faf.persistence;


import org.faf.persistence.db.CheckIn;
import org.faf.persistence.db.User;
import org.faf.persistence.util.Device;
import org.faf.persistence.util.GeoLocation;
import org.junit.Assert;
import org.junit.Test;

public class PersistenceManagerTest {

	@Test
	public void testCreateCheckIn() throws Exception {
		PersistenceManager pm = new PersistenceManager();
		pm.initializeDB();
		User user = new User("login","password");
		user.setId(0);
		GeoLocation geolocation = new GeoLocation(new Double(1),new Double(2));
		Device device = new Device("Mobile");
		CheckIn checkIn = new CheckIn(user,geolocation,device);
		pm.create(checkIn);
		CheckIn storedCheckIn = pm.read(checkIn.getId());
		Assert.assertEquals("The id element retrieved should be the same as the one stored", storedCheckIn,checkIn);
	}
	
}