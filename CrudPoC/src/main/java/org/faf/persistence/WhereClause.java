package org.faf.persistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WhereClause {

	Map<String,Object> criteria;
	
	public WhereClause(){
		criteria=new HashMap<String,Object>();
	}
	
	public void addCriteria(String name, Object value){
		criteria.put(name, value);
	}
	
	public String getClause(){
		String result="";
		for (String name : criteria.keySet()) {
			if(criteria.get(name)!=null){
				result+=name+"=? AND ";
			}
		}
		result=result.replaceFirst(" AND $", "");
		if(!result.trim().equals("")){
			return " WHERE "+result;
		}
		return result;
	}
	
	public Collection<Object> getValues(){
		return criteria.values();
	}
}
