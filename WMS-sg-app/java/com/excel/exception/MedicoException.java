package com.excel.exception;

public class MedicoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4351553084479332498L;
	
	public MedicoException() {
	}
	
	public MedicoException(String message) {
		super(message);
	}
	
	public MedicoException(String message,Throwable t){
		super(message, t);
	}

}
