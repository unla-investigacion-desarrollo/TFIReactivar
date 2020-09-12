package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long>{
	
	@Query("SELECT l FROM Login l WHERE l.email = ?1")
	public Login findByEmail(String email);

}
