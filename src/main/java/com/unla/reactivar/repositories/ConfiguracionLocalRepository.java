package com.unla.reactivar.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.ConfiguracionLocal;
import com.unla.reactivar.models.Emprendimiento;

@Repository
public interface ConfiguracionLocalRepository extends JpaRepository<ConfiguracionLocal, Long> {

	public ConfiguracionLocal findByIdConfiguracionLocal(Long id);
	
	@Query("select c from ConfiguracionLocal c where c.emprendimiento= ?1")
	public List<ConfiguracionLocal> findByEmprendimiento(Emprendimiento emprendimiento);

}
