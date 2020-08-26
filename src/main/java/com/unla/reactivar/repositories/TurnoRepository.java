package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long>{
	
	public Turno findByIdTurno(Long idTurno);
	
}
