package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Player;

@Repository
//CRUD for player with long as primary key
public interface PlayerRepository extends JpaRepository<Player, Long> 
{
	//JPA will automatically find players by team id (findBy) 
	List<Player> findByTeamId(Long teamId);
}
