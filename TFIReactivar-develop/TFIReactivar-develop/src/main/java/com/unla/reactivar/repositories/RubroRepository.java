package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.Rubro;

@Repository
public interface RubroRepository extends JpaRepository<Rubro, Long>{
	
	public Rubro findByIdRubro(Long idRubro);
	
}
