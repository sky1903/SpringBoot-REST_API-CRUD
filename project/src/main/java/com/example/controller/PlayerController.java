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

import com.example.exceptions.PlayerNotFoundException;
import com.example.model.Player;
import com.example.repository.PlayerRepository;

@RestController
public class PlayerController {
	

	//will inject PlayerRepository object to PlayerController
	@Autowired
	private PlayerRepository playerRepository;
	
		
//------------------------------------------------------------------------------
	
	//CREATE PLAYER//
	//by using POST method//
	@PostMapping("/create/player")
	public String createPlayer(@RequestBody Player player)
	{
		//getting all the values (using getter methods)
		player.getId();
		player.getAge();
		player.getCreated_at();
		player.getTeam_id();
		player.getUpdated_at();
		//save records
		playerRepository.save(player);
		//displaying message
		return "player created successfully";
	}
	
//-----------------------------------------------------------------------------
	
	//FIND PLAYER BY ID//
	//by using GET method //
	@GetMapping("/player/{id}")
	public Player getPlayerById(@PathVariable Long id) {
		
		Optional<Player> player = playerRepository.findById(id);
		if(!player.isPresent()) {
			//if not present then will throw custom exception
			throw new PlayerNotFoundException();
		}
		return player.get();
	}
	
	
//------------------------------------------------------------------------------	
	
	//UPDATE PLAYER INFORMATION//
	//by using PUT method//
	@PutMapping("/update/player/{id}")
	public String updatePlayer(@PathVariable Long id, @RequestBody Player player)
	{
		//first need to find the player by
		//if found then will move forward to update, else display null
		Player playerlist = playerRepository.findById(id).orElse(null);
		playerlist.setId(player.getId());
		playerlist.setAge(player.getAge());
		playerlist.setCreated_at(player.getCreated_at());
		playerlist.setName(player.getName());
		playerlist.setTeam_id(player.getTeam_id());
		playerlist.setUpdated_at(player.getUpdated_at());
		//save the records
		playerRepository.save(playerlist);
		//display message
		return "updated successfully";
	}
	
//------------------------------------------------------------------------------

	//DELETE PLAYER//
	//by using DELETE method//
	@DeleteMapping("/delete/player/{id}")
	public String deletePlayer(@PathVariable Long id)
	{
		playerRepository.deleteById(id);
		//return deleted message
		return "deleted successfully";
	}

//-----------------------------------------------------------------------------
	
	//LIST OF PLAYERS//
	//by using GET method
	@GetMapping("/players")
	public List<Player> listofPlayers()
	{
		return playerRepository.findAll();
	}
	
//------------------------------------------------------------------------------	
		
	//FIND PLAYERS BY TEAM ID//
	//by using get method
	@GetMapping("/teamplayers/{id}")
	public List<Player> playersByTeamId(@PathVariable("id") Long teamId)
	{
		return playerRepository.findByTeamId(teamId);
		
	}
	 
}
