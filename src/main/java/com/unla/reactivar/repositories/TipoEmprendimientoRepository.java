package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.TipoEmprendimiento;

@Repository
public interface TipoEmprendimientoRepository extends JpaRepository<TipoEmprendimiento, Long>{
	
	public TipoEmprendimiento findByIdTipoEmprendimiento(Long id);

}
