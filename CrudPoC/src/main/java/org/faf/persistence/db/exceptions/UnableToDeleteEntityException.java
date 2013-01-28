package org.faf.persistence.db.exceptions;

public class UnableToDeleteEntityException extends Exception {

	public UnableToDeleteEntityException(){
		super();
	}
	
	public UnableToDeleteEntityException(Throwable cause){
		super(cause);
	}
	
}
