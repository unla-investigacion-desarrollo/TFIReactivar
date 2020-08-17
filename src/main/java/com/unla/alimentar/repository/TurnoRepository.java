package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long>{
	
	public Turno findByIdTurno(Long idTurno);
	
}
