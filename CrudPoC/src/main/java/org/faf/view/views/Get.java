package org.faf.view.views;

import java.util.List;

import org.faf.persistence.PersistenceEntity;
import org.faf.view.View;

public class Get implements View {

	private List<PersistenceEntity> entities;

	public Get(List<PersistenceEntity> entities) {
		this.entities=entities;
	}

	@Override
	public String render() {
		if(entities==null){
			return "Sorry, there was a problem and we could not retrieve the data requested.";
		}else if(entities.size()==0){
			return "No elements found.";
		}else{
			return entities.get(0).getClass().getSimpleName()+" list: \n"+entities.toString();
		}
	}

}
