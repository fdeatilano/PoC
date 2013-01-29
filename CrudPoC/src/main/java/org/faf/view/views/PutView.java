package org.faf.view.views;

import org.faf.persistence.PersistenceEntity;
import org.faf.view.View;

public class PutView implements View{

	PersistenceEntity entity;
	
	public PutView(PersistenceEntity entity) {
		this.entity=entity;
	}

	@Override
	public String render() {
		if(entity==null){
			return "Sorry, there was a problem and we could not store the resource in our database.";
		}else{
			return "The following "+entity.getClass().getSimpleName()+" was successfully stored: \n"+entity.toString();
		}
	}

}
