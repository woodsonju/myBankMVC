package com.wj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wj.dao.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{
	
}
