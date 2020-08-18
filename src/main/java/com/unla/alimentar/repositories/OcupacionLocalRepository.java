package com.unla.alimentar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.alimentar.models.OcupacionLocal;

@Repository
public interface OcupacionLocalRepository extends JpaRepository<OcupacionLocal, Long>{
	
	public OcupacionLocal findByIdOcupacionLocal(Long idOcupacionLocal);
	
}
