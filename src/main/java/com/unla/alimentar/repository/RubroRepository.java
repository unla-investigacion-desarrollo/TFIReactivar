package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.Rubro;

@Repository
public interface RubroRepository extends JpaRepository<Rubro, String>{
	
	public Rubro findByIdRubro(String idRubro);
	
}
