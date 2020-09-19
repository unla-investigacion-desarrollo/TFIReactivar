package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.EstadoPersona;

@Repository
public interface EstadoPersonaRepository extends JpaRepository<EstadoPersona, Long>{
	
	public EstadoPersona findByIdEstadoPersona(Long idEstadoPersona);
	
}
