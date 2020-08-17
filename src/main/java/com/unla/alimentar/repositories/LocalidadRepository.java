package com.unla.alimentar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.models.Localidad;

@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, Long>{
	
	public Localidad findByIdLocalidad(Long id);

}
