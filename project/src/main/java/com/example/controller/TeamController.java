package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.TeamNotFoundException;
import com.example.model.Team;
import com.example.repository.TeamRepository;

@RestController 
public class TeamController {
	
	//will inject teamRepository object into TeamController class
	@Autowired
	private TeamRepository teamRepository;

//---------------------------------------------------------------------------------------------------
	
	//CREATE TEAM//
	//by using POST method//
	@PostMapping("/create/team")
	//mapping team details from request to bean (@RequestBody)
	public String createTeam(@RequestBody Team team)
	{
		//getting all the values (using getter methods)
		//enter column names and its values in json format (postman)
		//if skip any column then will insert null value in that column (in database)
		team.getId();
		team.getName();
		team.getLocation();
		team.getCreated_at();
		team.getUpdated_at();
		//save the records
		teamRepository.save(team);
		//displaying created message
		return "team created successfully";
	}
	
//--------------------------------------------------------------------------------------------------------
	
	//FIND TEAM BY ID//
	//by using GET method//
	@GetMapping("/team/{id}")
	public Team getTeamById(@PathVariable int id)
	{
		Optional<Team> teamList = teamRepository.findById(id);
		if(!teamList.isPresent()) {
			throw new TeamNotFoundException();
		}
		//get all the fields
		return teamList.get();
	}
//---------------------------------------------------------------------------------------------------------	
	
	//UPDATE TEAM INFORMATION//
	//by using PUT method//
	//setting (select options)------> body - raw - JSON (application) - Enter records
	//first enter all the fields (of specific id) in the postman then update whichever the field you want
	@PutMapping("/update/team/{id}")
	public String updateTeam(@PathVariable int id,@RequestBody Team team)
	{
		Team teamlist = teamRepository.findById(id).orElse(null);
		teamlist.setId(team.getId());
		teamlist.setName(team.getName());
		teamlist.setLocation(team.getLocation());
		teamlist.setCreated_at(team.getCreated_at());
		teamlist.setUpdated_at(team.getUpdated_at());
		//saving the updated records
		teamRepository.save(teamlist);
		//displaying updated message
		return "updated successfully";
	
	}
		
//-------------------------------------------------------------------------------------------	
	
	//DELETE TEAM //
	//by using DELETE method//
	@DeleteMapping("/delete/team/{id}")
	public String deleteTeam(@PathVariable int id)
	{
		teamRepository.deleteById(id);
		//return deleted message
		return "deleted successfully";
	}
	
//-------------------------------------------------------------------------------------------
	
	//LIST OF TEAMS//
	//by using get method
	@GetMapping("/teams")
	public List<Team> listofTeam()
	{
		return teamRepository.findAll();
	}
	
//--------------------------------------------------------------------------------------------
	
	//FIND TEAM BY PLAYER ID//
	//by using get method
	@GetMapping("/teamByPlayerId/{id}")
	public Team teamByPlayerId(@PathVariable Long id)
	{
		return teamRepository.findByPlayerId(id);
	}
	
}
