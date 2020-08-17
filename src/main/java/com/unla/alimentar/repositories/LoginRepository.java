package com.unla.alimentar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.models.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long>{
	
	public Login findByIdLogin(Long id);

}
