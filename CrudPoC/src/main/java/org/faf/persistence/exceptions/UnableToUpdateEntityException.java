package org.faf.persistence.exceptions;

public class UnableToUpdateEntityException extends Exception {

	public UnableToUpdateEntityException(){
		super();
	}
	
	public UnableToUpdateEntityException(Throwable cause){
		super(cause);
	}
}
