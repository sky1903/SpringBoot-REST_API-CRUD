package com.example.exceptions;

public class PlayerNotFoundException extends RuntimeException{ //extends unchecked exception

	public PlayerNotFoundException() {
		// TODO Auto-generated constructor stub
		
		//calling superclass(RunTimeException) contructor - string paramatarized
		super("Player not Found with id ");
	}
}
