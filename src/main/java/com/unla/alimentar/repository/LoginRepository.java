package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long>{
	
	public Login findByIdLogin(Long id);

}
