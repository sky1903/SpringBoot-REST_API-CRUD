package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer>{
	Team findByPlayerId(Long id);
}
