package org.faf.view.views;

import org.faf.persistence.PersistenceEntity;
import org.faf.view.View;

public class Post implements View {

	PersistenceEntity entity;
	
	public Post(PersistenceEntity entity) {
		this.entity=entity;
	}

	@Override
	public String render() {
		if(entity==null){
			return "Sorry, there was a problem and we could not update the resource in our database.";
		}else{
			return "The following "+entity.getClass().getSimpleName()+" was successfully updated: \n"+entity.toString();
		}
	}

}
