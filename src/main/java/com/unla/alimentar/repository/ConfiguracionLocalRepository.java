package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.ConfiguracionLocal;

@Repository
public interface ConfiguracionLocalRepository extends JpaRepository<ConfiguracionLocal, Long>{
	
	public ConfiguracionLocal findByIdConfiguracionLocal(Long id);

}
