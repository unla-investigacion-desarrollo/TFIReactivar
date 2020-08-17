package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.Rubro;

@Repository
public interface RubroRepository extends JpaRepository<Rubro, Long>{
	
	public Rubro findByIdRubro(Long idRubro);
	
}
