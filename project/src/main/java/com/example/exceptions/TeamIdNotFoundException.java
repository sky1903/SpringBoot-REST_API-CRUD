package com.example.exceptions;

public class TeamIdNotFoundException extends RuntimeException { //extends unchecked exception
	
	public TeamIdNotFoundException() {
		//superclass constructor calling
		super("please enter valid team id");
	}
	
}
