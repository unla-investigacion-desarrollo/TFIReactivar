package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.Ubicacion;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Long>{
	
	public Ubicacion findByIdUbicacion(Long id);

}
