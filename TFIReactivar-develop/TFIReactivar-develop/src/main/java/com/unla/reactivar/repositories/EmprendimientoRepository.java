package com.unla.reactivar.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.Emprendimiento;

@Repository
public interface EmprendimientoRepository extends JpaRepository<Emprendimiento, Long>{

	public Emprendimiento findByIdEmprendimiento(Long id);
	
	@Query(value= "{call sp_mostrarPorRubro(:id_rubro)}", nativeQuery= true)
	List<Emprendimiento> traerPorRubro(@Param("id_rubro") long idRubro);
	
}