package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "player")
public class Player {
	
	@Id
	private Long id;
	private String name;
	private int age;
	private String created_at;
	private String updated_at;
	
	@Column(name = "team_id")	//team_id (column name from database)
	private Long teamId;
	
	
	public Player(int id, String name, int age, String created_at, String updated_at, Long teamId) {
		super();
		this.name = name; 
		this.age = age;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.teamId = teamId;
	}
	
	public Player() {
		
	}
	//Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public Long getTeam_id() {
		return teamId;
	}
	public void setTeam_id(Long team_id) {
		this.teamId = team_id;
	}
}
