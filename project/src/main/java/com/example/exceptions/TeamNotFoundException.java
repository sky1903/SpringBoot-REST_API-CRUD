package com.example.exceptions;

public class TeamNotFoundException extends RuntimeException { //extends unchecked exception

	public TeamNotFoundException() {
		
		//calling superclass(RunTimeException) contructor - string paramatarized
		super("team not found");  

	}
}
