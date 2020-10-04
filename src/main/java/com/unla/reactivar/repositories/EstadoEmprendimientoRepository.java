package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.EstadoEmprendimiento;

@Repository
public interface EstadoEmprendimientoRepository extends JpaRepository<EstadoEmprendimiento, Long> {

	public EstadoEmprendimiento findByIdEstadoEmprendimiento(Long idEstadoEmprendimiento);

}
