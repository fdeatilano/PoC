package org.faf.view.views;

import org.faf.persistence.PersistenceEntity;
import org.faf.view.View;

public class Delete implements View {

	PersistenceEntity entity;
	
	public Delete(PersistenceEntity entity) {
		this.entity=entity;
	}

	@Override
	public String render() {
		if(entity==null){
			return "Sorry, there was a problem and we could not delete the resource from our database.";
		}else{
			return "The "+entity.getClass().getSimpleName()+" with identifier "+entity.getId()+" was successfully deleted.";
		}
	}

}
