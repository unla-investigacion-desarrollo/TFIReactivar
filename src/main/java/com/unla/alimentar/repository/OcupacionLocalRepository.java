package com.unla.alimentar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.modelo.OcupacionLocal;

@Repository
public interface OcupacionLocalRepository extends JpaRepository<OcupacionLocal, Long>{
	
	public OcupacionLocal findByIdOcupacionLocal(Long idOcupacionLocal);
	
}
