package com.unla.reactivar.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.EstadoTurno;
import com.unla.reactivar.models.Persona;
import com.unla.reactivar.models.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

	public Turno findByIdTurno(Long idTurno);
	
	@Query("SELECT t FROM Turno t WHERE t.emprendimiento= ?1 and t.estadoTurno= ?2")
	public List<Turno> findTurnosPorEmprendimiento(Emprendimiento emp, EstadoTurno estadoTurno);

	@Query("SELECT t FROM Turno t WHERE t.persona= ?1 and t.estadoTurno= 1")
	public List<Turno> findByPersona(Persona persona);

}
