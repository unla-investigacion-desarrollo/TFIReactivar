package com.unla.alimentar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.models.Rubro;

@Repository
public interface RubroRepository extends JpaRepository<Rubro, Long>{
	
	public Rubro findByIdRubro(Long idRubro);
	
}
