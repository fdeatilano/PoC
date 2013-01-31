package org.faf.controller;

import org.faf.persistence.entities.User;
import org.junit.Test;

public class FrontControlerTest {
	
	@Test
	public void testBuildUser() throws Exception {
		FrontController fc = new FrontController();
		User user = new User("login", "password", "role");
//		HttpServletRequest req = new HttpServletRequestWrapper(null);
//		PersistenceEntity entity = fc.buildEntity(req);
	}
}
