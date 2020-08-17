package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.ArticuloReferencia;

@Repository
public interface ArticuloReferenciaRepository extends JpaRepository<ArticuloReferencia, Long>{
	
	public ArticuloReferencia findByIdArticuloReferencia(Long idArticuloReferencia);
	
}
