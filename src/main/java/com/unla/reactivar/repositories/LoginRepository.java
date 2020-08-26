package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long>{
	
	public Login findByIdLogin(Long id);

}
