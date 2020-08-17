package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.Emprendimiento;

@Repository
public interface EmprendimientoRepository extends JpaRepository<Emprendimiento, Long>{

	public Emprendimiento findByIdEmprendimiento(Long id);
	
}
