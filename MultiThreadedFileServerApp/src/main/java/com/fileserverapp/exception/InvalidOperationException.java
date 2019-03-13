/*
 * custom exception class for handling various error handling conditions based on the client input
 * 
 * */

package com.fileserverapp.exception;

public class InvalidOperationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidOperationException() {

	}

	public InvalidOperationException(String message) {
		super(message);
	}

	public InvalidOperationException(String message, Throwable cause) {
		super(message, cause);
	}

}

