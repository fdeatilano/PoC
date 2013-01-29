package org.faf.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import org.faf.persistence.PersistenceEntity;
import org.faf.persistence.PersistenceManager;
import org.faf.persistence.WhereClause;
import org.faf.persistence.config.DbConfiguration.UsersFields;
import org.faf.persistence.entities.User;
import org.faf.persistence.exceptions.UnableToCreateEntityException;
import org.faf.persistence.exceptions.UnableToRetrieveEntityException;
import org.faf.persistence.exceptions.UnableToRetrieveIdException;
import org.faf.view.View;
import org.faf.view.views.GetView;
import org.faf.view.views.PutView;
import org.faf.view.views.RoleView;

public class CrudController {

	public RoleView authenticate(String login, String password) {
		PersistenceManager pm = new PersistenceManager();
		
		WhereClause where = new WhereClause();
		where.addCriteria(UsersFields.LOGIN.name(), login);
		where.addCriteria(UsersFields.PASSWORD.name(), convertToMD5(password));
		User userStored;
		try {
			userStored = (User) pm.read(User.class, where).get(0);
		} catch (UnableToRetrieveEntityException | UnableToRetrieveIdException e) {
			e.printStackTrace();
			return null;
		}
		if(userStored!=null){
			return new RoleView(userStored.getRole());
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
			pm.create(entity);
		} catch (UnableToCreateEntityException | UnableToRetrieveIdException e) {
			e.printStackTrace();
			return new PutView(null);
		}
		return new PutView(entity);
	}

	public View get(PersistenceEntity entity) {
		PersistenceManager pm = new PersistenceManager();
		Map<String,Object> fieldsValues = entity.getFieldsAndValues();
		WhereClause where = new WhereClause();
		for (String field : fieldsValues.keySet()) {
			where.addCriteria(field, fieldsValues.get(field));
		}
		List<PersistenceEntity> entities = null;
		try {
			entities = pm.read(entity.getClass(), where);
		} catch (UnableToRetrieveEntityException | UnableToRetrieveIdException e) {
			e.printStackTrace();
			return new GetView(null);
		}
		return new GetView(entities);
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
