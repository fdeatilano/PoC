PoC
=======

Proofs of concept developed as open source.

Build and Install:
------------------------

- Download an install maven
- Check out the code from repository
- Open a command line tool and go to the CrudClient folder where the pom.xml file is located
- Run the following command:

	mvn clean assembly:assembly
	
- A CrudClient.jar file will be generated in the foler CrudClient/target


Use Guide for CrudClient
-------------------------

- Launch the application with java -jar CrudClient.jar
- A prompt will display in this form:

	http-query>

- To list all available commands enter ?list
- To get detailed info on a command enter ?help command-name
- Examples of valid calls:
	
	Retrieve all users stored in database:
	
		http-query> get users
	
	Retrieve places with longitude 3.32:
	
		http-query> get places longitude=3.32
		
	Creates a new user with role User or Administrator:
	
		http-query> put users login=login&password=pass&role=[User|Administrator]
	
	Updates an existing checkin:
	
		http-query> post checkins id=2&user_id=1&place_id=1&longitude=2.9&latitude=8.9&device=Phone
	
	Deletes an existing place:
	
		http-query> delete places id=1
	
	
