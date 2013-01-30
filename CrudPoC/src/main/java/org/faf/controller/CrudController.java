package org.faf.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.faf.persistence.PersistenceEntity;
import org.faf.persistence.PersistenceManager;
import org.faf.persistence.WhereClause;
import org.faf.persistence.config.DbConfiguration;
import org.faf.persistence.config.DbConfiguration.UsersFields;
import org.faf.persistence.entities.User;
import org.faf.persistence.exceptions.UnableToCreateEntityException;
import org.faf.persistence.exceptions.UnableToDeleteEntityException;
import org.faf.persistence.exceptions.UnableToRetrieveEntityException;
import org.faf.persistence.exceptions.UnableToRetrieveIdException;
import org.faf.persistence.exceptions.UnableToUpdateEntityException;
import org.faf.view.View;
import org.faf.view.views.Delete;
import org.faf.view.views.Get;
import org.faf.view.views.Post;
import org.faf.view.views.Put;
import org.faf.view.views.Role;

public class CrudController {

	public View authenticate(String login, String password) {
		PersistenceManager pm = new PersistenceManager();
		
		WhereClause where = new WhereClause();
		where.addCriteria(UsersFields.LOGIN.name(), login);
		where.addCriteria(UsersFields.PASSWORD.name(), convertToMD5(password));
		User userStored;
		try {
			List<PersistenceEntity> entities = pm.read(User.class, where);
			if(entities==null){
				return null;
			}else{
				userStored = (User) entities.get(0);
			}
		} catch (UnableToRetrieveEntityException e){
			e.printStackTrace();
			return null;
		} catch (UnableToRetrieveIdException e) {
			e.printStackTrace();
			return null;
		}
		if(userStored!=null){
			return new Role(userStored.getRole());
		}else{
			return null;
		}
	}
	
	public View put(PersistenceEntity entity) {
		PersistenceManager pm = new PersistenceManager();
		try {
			if(entity.getClass().equals(User.class)){
				User user = (User)entity;
				String pswHash = convertToMD5(user.getPassword());
				user.setPassword(pswHash);
			}
			entity=pm.create(entity);
		} catch (UnableToCreateEntityException e){
			e.printStackTrace();
			return new Put(null);
		} catch (UnableToRetrieveIdException e) {
			e.printStackTrace();
			return new Put(null);
		}
		return new Put(entity);
	}

	public View get(PersistenceEntity entity) {
		PersistenceManager pm = new PersistenceManager();
		List<PersistenceEntity> entities = null;
		Map<String,Object> fieldsValues = entity.getFieldsAndValues();
		WhereClause where = new WhereClause();
		if(entity.getId()!=null){
			where.addCriteria(DbConfiguration.DB_IDENTIFIER_FIELD, entity.getId());
		}
		for (String field : fieldsValues.keySet()) {
			where.addCriteria(field, fieldsValues.get(field));
		}
		try {
			entities = pm.read(entity.getClass(), where);
		} catch (UnableToRetrieveEntityException e){
			e.printStackTrace();
			return new Get(null);	
		} catch (UnableToRetrieveIdException e) {
			e.printStackTrace();
			return new Get(null);
		}
		return new Get(entities);
	}

	public Post post(PersistenceEntity entity) {
		PersistenceManager pm = new PersistenceManager();
		try {
			if(entity.getClass().equals(User.class)){
				User user = (User)entity;
				String pswHash = convertToMD5(user.getPassword());
				user.setPassword(pswHash);
			}
			pm.update(entity);
		} catch (UnableToUpdateEntityException e) {
			e.printStackTrace();
			return new Post(null);
		}
		return new Post(entity);
	}
	
	public View delete(PersistenceEntity entity) {
		PersistenceManager pm = new PersistenceManager();
		try {
			pm.delete(entity.getClass(), entity.getId());
		} catch (UnableToDeleteEntityException e) {
			e.printStackTrace();
			return new Delete(null);
		}
		return new Delete(entity);
		
	}

	public void initialize() {
		PersistenceManager pm = new PersistenceManager();
		try {
			pm.initializeDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String convertToMD5(String input){
        
        String md5 = null;
        if(null == input) return null;
        try {
	        MessageDigest digest = MessageDigest.getInstance("MD5");
	        digest.update(input.getBytes());
	        BigInteger bi = new BigInteger(1, digest.digest());
	        md5 = bi.toString(16);
	        
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

}
