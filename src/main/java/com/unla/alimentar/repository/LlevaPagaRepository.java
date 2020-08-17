package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.LlevaPaga;

@Repository
public interface LlevaPagaRepository extends JpaRepository<LlevaPaga, Long>{
	
	public LlevaPaga findByIdLlevaPaga(Long idLlevaPaga);
	
}
