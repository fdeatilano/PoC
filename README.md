PoC
=======

Proofs of concept developed as open source.

Build and Install:
------------------------

- Download an install maven
- Check out the code from repository
- Open a command line tool and go to the CrudPoC folder where the pom.xml file is located
- Run the following command:
	mvn clean install
- The application tests will be run and a CrudPoC.war file will be generated in the foler CrudPoC/target
- Copy the CrudPoC.war file to the webapps folder of tomcat and start it.


Use Guide for CrudPoC
-------------------------

- The application will be deployed in http://localhost:8080/CrudPoC/controller and the database initialized.
- Before being able to use the application you must authenticate with a client that manages HttpSessions. 
  To do so make a Http GET request providing authentication data in this format:
  
		http://localhost:8080/CrudPoC/controller/authenticate?login=admin&password=admin
	
  The default Administrator account is deployed when the application is deployed with the credentials admin:admin
  
  
- The interface of the application simulates a SOAP service and the application offers all CRUD operations over the entities it manages, which are:

	- users
	- places
	- checkins
	
	
	
- Permissions:

	- Administrator role can perform any action.
	- User role can authenticate, read data from all entities and put checkins.
	
	
	
- Examples of valid calls:
	
	Retrieves all users stored in database:
	
		GET http://localhost:8080/CrudPoC/controller/users
	
	Retrieves all places stored in database:
	
		GET http://localhost:8080/CrudPoC/controller/places
	
	Retrieves all checkins stored in database:
	
		GET http://localhost:8080/CrudPoC/controller/checkins
	
	Creates a new user with role User or Administrator:
	
		PUT http://localhost:8080/CrudPoC/controller/users?login=login&password=pass&role=[User|Administrator]
	
	Creates a new place:
	
		PUT http://localhost:8080/CrudPoC/controller/places?longitude=2.34&latitude=8.90&address=Almond Street
	
	Creates a new checkin:
	
		PUT http://localhost:8080/CrudPoC/controller/checkins?user_id=1&place_id=1&longitude=2.9&latitude=8.9&device=Phone
	
	Updates an existing user:
	
		POST PUT http://localhost:8080/CrudPoC/controller/users?id=1&login=login&password=pass&role=[User|Administrator]
	
	Updates an existing place:
	
		POST http://localhost:8080/CrudPoC/controller/places?id=3&longitude=2.34&latitude=8.90&address=Almond Street
	
	Updates an existing checkin:
	
		POST http://localhost:8080/CrudPoC/controller/checkins?id=2&user_id=1&place_id=1&longitude=2.9&latitude=8.9&device=Phone
	
	Deletes an existing user:
	
		DELETE http://localhost:8080/CrudPoC/controller/users?id=1
	
	Deletes an existing place:
	
		DELETE http://localhost:8080/CrudPoC/controller/places?id=1
	
	Deletes an existing checkin:
	
		DELETE http://localhost:8080/CrudPoC/controller/checkins?id=1
