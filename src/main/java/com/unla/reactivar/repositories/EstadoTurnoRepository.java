package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.EstadoTurno;

@Repository
public interface EstadoTurnoRepository extends JpaRepository<EstadoTurno, Long> {

	public EstadoTurno findByIdEstadoTurno(Long idEstadoTurno);

}
