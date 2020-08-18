package com.unla.alimentar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.models.ArticuloReferencia;

@Repository
public interface ArticuloReferenciaRepository extends JpaRepository<ArticuloReferencia, Long>{
	
	public ArticuloReferencia findByIdArticuloReferencia(Long idArticuloReferencia);
	
}
