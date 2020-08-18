package com.unla.alimentar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.models.ConfiguracionLocal;

@Repository
public interface ConfiguracionLocalRepository extends JpaRepository<ConfiguracionLocal, Long>{
	
	public ConfiguracionLocal findByIdConfiguracionLocal(Long id);

}
