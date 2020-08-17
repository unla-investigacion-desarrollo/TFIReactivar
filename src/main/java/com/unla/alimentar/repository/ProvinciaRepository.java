package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.Provincia;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long>{
	
	public Provincia findByIdProvincia(Long id);

}
