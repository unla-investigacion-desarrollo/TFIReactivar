package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long>{
	
	public Marca findByIdMarca(Long idMarca);
	
}
