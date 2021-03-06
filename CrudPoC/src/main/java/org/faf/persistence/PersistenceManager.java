package org.faf.persistence;

import static org.faf.config.AppConfiguration.DB_IDENTIFIER_FIELD;
import static org.faf.config.AppConfiguration.DB_PASSWORD;
import static org.faf.config.AppConfiguration.DB_URI;
import static org.faf.config.AppConfiguration.DB_USER;
import static org.faf.config.AppConfiguration.INITIAL_PARAM_INDEX;
import static org.faf.persistence.util.JdbcUtils.executeScript;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.faf.persistence.exceptions.UnableToCreateEntityException;
import org.faf.persistence.exceptions.UnableToDeleteEntityException;
import org.faf.persistence.exceptions.UnableToRetrieveEntityException;
import org.faf.persistence.exceptions.UnableToRetrieveIdException;
import org.faf.persistence.exceptions.UnableToUpdateEntityException;

public class PersistenceManager {


	/**
	 * Inserts into database the given entity
	 * 
	 * @param entity
	 * @return
	 * @throws UnableToCreateEntityException
	 * @throws UnableToRetrieveIdException
	 */
	public PersistenceEntity create(PersistenceEntity entity) throws UnableToCreateEntityException, UnableToRetrieveIdException {
		Connection conn;
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(DB_URI, DB_USER, DB_PASSWORD);
			
			String insertQuery = "INSERT INTO " + entity.getTableName() + " (";
			Map<String, Object> fieldsValues = entity.getFieldsAndValues();
			String valuesStr="";
			List<Object> values = new LinkedList<Object>();
			for (String field : fieldsValues.keySet()) {
				if(fieldsValues.get(field)!=null){
					insertQuery+=field+",";
					valuesStr+="?,";
					values.add(fieldsValues.get(field));
				}		
			}
			insertQuery=insertQuery.replaceFirst(",$", ") VALUES (");
			valuesStr=valuesStr.replaceFirst(",$",")");
			insertQuery+=valuesStr;
			
			PreparedStatement prepStmInsert = conn.prepareStatement(insertQuery);
			Integer paramIndex = INITIAL_PARAM_INDEX;
			for (Object value : values) {
				if(value instanceof Integer){
					prepStmInsert.setInt(++paramIndex, (Integer)value);
				}else if(value instanceof Double){
					prepStmInsert.setDouble(++paramIndex, (Double)value);
				}else if(value instanceof String){
					prepStmInsert.setString(++paramIndex, (String)value);
				}
			}
			if(prepStmInsert.executeUpdate()!=1){
				throw new UnableToCreateEntityException();
			}
			prepStmInsert.close();
			
			String getIdentityQuery="CALL IDENTITY()";
			PreparedStatement prepStmIdentity = conn.prepareStatement(getIdentityQuery);
			ResultSet rsIdentity = prepStmIdentity.executeQuery();
			if(rsIdentity.next()){
				Integer identity = rsIdentity.getInt(1);
				if(identity==0){
					throw new UnableToRetrieveIdException();
				}
				entity.setId(identity);
			}else{
				throw new UnableToRetrieveIdException();
			}
			rsIdentity.close();
			prepStmIdentity.close();
			conn.close();
			
			entity = read(entity.getClass(),entity.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UnableToCreateEntityException(e);
		} catch (UnableToRetrieveEntityException e) {
			e.printStackTrace();
			throw new UnableToCreateEntityException(e);
		}
		return entity;
	}

	/**
	 * Retrieves from database the entities that match the criteria provided by the where clause
	 * 
	 * @param entityClass
	 * @param where clause storing the search criteria
	 * @return
	 * @throws UnableToRetrieveEntityException
	 * @throws UnableToRetrieveIdException
	 */
	public List<PersistenceEntity> read(Class<?> entityClass, WhereClause where) throws UnableToRetrieveEntityException, UnableToRetrieveIdException {
		try {
			try {
				Class.forName("org.hsqldb.jdbcDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			PersistenceEntity entity = (PersistenceEntity)entityClass.newInstance();
			Connection conn = DriverManager.getConnection(DB_URI, DB_USER, DB_PASSWORD);
			
			String selectQuery = "SELECT ";
			Map<String, Class<?>> fieldsTypes = entity.getFieldsAndTypes();
			for (String field : fieldsTypes.keySet()) {
				selectQuery+=field+",";
			}
			selectQuery=selectQuery.replaceFirst(",$", "");
			selectQuery+=" FROM " + entity.getTableName();
			selectQuery+=where.getClause();
		
			PreparedStatement prepStmSelect = conn.prepareStatement(selectQuery);
			Integer paramIndex = INITIAL_PARAM_INDEX;
			for (Object value : where.getValues()) {
				if(value instanceof Integer){
					prepStmSelect.setInt(++paramIndex, (Integer)value);
				}else if(value instanceof Double){
					prepStmSelect.setDouble(++paramIndex, (Double)value);
				}else if(value instanceof String){
					prepStmSelect.setString(++paramIndex, (String)value);
				}else if(value instanceof Timestamp){
					prepStmSelect.setTimestamp(++paramIndex, (Timestamp)value);
				}
			}
			ResultSet rs = prepStmSelect.executeQuery();
			LinkedHashMap<String, Object> values = new LinkedHashMap<String, Object>();
			List<PersistenceEntity> entities = new LinkedList<PersistenceEntity>();
			while(rs.next()){
				for (String field : fieldsTypes.keySet()) {
					if(fieldsTypes.get(field).equals(Integer.class)){
						Integer intValue = rs.getInt(field);
						values.put(field, intValue);
					}else if(fieldsTypes.get(field).equals(Double.class)){
						Double doubleValue = rs.getDouble(field);
						values.put(field, doubleValue);
					}else if(fieldsTypes.get(field).equals(String.class)){
						String strValue = rs.getString(field);
						values.put(field, strValue);
					}else if(fieldsTypes.get(field).equals(Timestamp.class)){
						Timestamp timestampValue = rs.getTimestamp(field);
						values.put(field, timestampValue);
					}	
				}
				PersistenceEntity actualEntity = (PersistenceEntity)entityClass.newInstance();
				actualEntity.setValues(values);
				entities.add(actualEntity);
			}
			prepStmSelect.close();
			return entities;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UnableToRetrieveEntityException(e);
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new UnableToRetrieveEntityException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new UnableToRetrieveEntityException(e);
		}
	}
	
	/**
	 * Retrieves from database the entity with the identifier and types provided
	 * 
	 * @param entityClass
	 * @param identifier
	 * @return
	 * @throws UnableToRetrieveEntityException
	 * @throws UnableToRetrieveIdException
	 */
	public PersistenceEntity read(Class<?> entityClass, Integer identifier) throws UnableToRetrieveEntityException, UnableToRetrieveIdException {
			
		if(identifier==null){
			throw new UnableToRetrieveEntityException();
		}else{
			WhereClause where = new WhereClause();
			where.addCriteria(DB_IDENTIFIER_FIELD, identifier);
			List<PersistenceEntity> entities = read(entityClass,where);
			if(entities.size()==0){
				return null;
			}else{
				return entities.get(0);
			}
		}		
	}
	
	/**
	 * The entity provided is updated in database with the given values
	 * 
	 * @param entity
	 * @return
	 * @throws UnableToUpdateEntityException
	 */
	public PersistenceEntity update(PersistenceEntity entity) throws UnableToUpdateEntityException {
		Connection conn;
		
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(DB_URI, DB_USER, DB_PASSWORD);
			
			String updateQuery = "UPDATE " + entity.getTableName() + " SET ";
			Map<String, Object> fieldsValues = entity.getFieldsAndValues();
			List<Object> values = new LinkedList<Object>();
			for (String field : fieldsValues.keySet()) {
				if(fieldsValues.get(field)!=null){
					updateQuery+=field+"=?,";
					values.add(fieldsValues.get(field));
				}		
			}
			updateQuery=updateQuery.replaceFirst(",$", " WHERE ");
			updateQuery+=DB_IDENTIFIER_FIELD +"=?";
			
			PreparedStatement prepStmUpdate = conn.prepareStatement(updateQuery);
			Integer paramIndex = INITIAL_PARAM_INDEX;
			for (Object value : values) {
				if(value instanceof Integer){
					prepStmUpdate.setInt(++paramIndex, (Integer)value);
				}else if(value instanceof Double){
					prepStmUpdate.setDouble(++paramIndex, (Double)value);
				}else if(value instanceof String){
					prepStmUpdate.setString(++paramIndex, (String)value);
				}
			}
			if(entity.getId()==null){
				throw new UnableToUpdateEntityException();
			}
			prepStmUpdate.setInt(++paramIndex, entity.getId());
			if(prepStmUpdate.executeUpdate()!=1){
				throw new UnableToUpdateEntityException();
			}
			prepStmUpdate.close();
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UnableToUpdateEntityException(e);
		}
		return entity;
	}
	
	/**
	 * Delete the register from database which has the same id provided in the parameter entity
	 * 
	 * @param entityClass
	 * @param identifier
	 * @throws UnableToDeleteEntityException
	 */
	public void delete(Class<?> entityClass, Integer identifier) throws UnableToDeleteEntityException {
		if(identifier==null){
			throw new UnableToDeleteEntityException();
		}else{
			try {
				Class.forName("org.hsqldb.jdbcDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				PersistenceEntity entity = (PersistenceEntity)entityClass.newInstance();
				Connection conn = DriverManager.getConnection(DB_URI, DB_USER, DB_PASSWORD);
				
				String deleteQuery = "DELETE FROM "+entity.getTableName();
				deleteQuery+=" WHERE " + DB_IDENTIFIER_FIELD +"=?";
			
				PreparedStatement prepStmDelete = conn.prepareStatement(deleteQuery);
				Integer paramIndex = INITIAL_PARAM_INDEX;
				prepStmDelete.setInt(++paramIndex, identifier);
				if(prepStmDelete.executeUpdate()!=1){
					throw new UnableToDeleteEntityException();
				}
				prepStmDelete.close();
				prepStmDelete.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new UnableToDeleteEntityException(e);
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw new UnableToDeleteEntityException(e);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new UnableToDeleteEntityException(e);
			}
		}
	}
	
	public void initializeDB() throws SQLException {
		try{
			executeScript("deleteDB.sql");
		}catch(SQLSyntaxErrorException ssee){ssee.printStackTrace();}
		try{
			executeScript("createDB.sql");
		}catch(SQLSyntaxErrorException ssee){ssee.printStackTrace();}
		try{
			executeScript("initializeDB.sql");
		}catch(SQLSyntaxErrorException ssee){ssee.printStackTrace();}
	}
	
}
