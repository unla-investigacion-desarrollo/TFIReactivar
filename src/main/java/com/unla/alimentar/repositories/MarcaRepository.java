package com.unla.alimentar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.models.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long>{
	
	public Marca findByIdMarca(Long idMarca);
	
}
