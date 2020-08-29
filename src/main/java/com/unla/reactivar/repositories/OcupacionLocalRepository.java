package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.OcupacionLocal;

@Repository
public interface OcupacionLocalRepository extends JpaRepository<OcupacionLocal, Long>{
	
	public OcupacionLocal findByIdOcupacionLocal(Long idOcupacionLocal);
	
}
