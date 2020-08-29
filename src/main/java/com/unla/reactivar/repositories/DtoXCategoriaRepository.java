package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.models.DtoXCategoria;

@Repository
public interface DtoXCategoriaRepository extends JpaRepository<DtoXCategoria, Long>{
	
	//@Query("SELECT * FROM Promocion p WHERE p.idPromocion = ?1")
	public DtoXCategoria findByIdPromocion(Long idDtoXCategoria);
	/*
	@Modifying
	@Query("SELECT p FROM Promocion p WHERE tipoPromocion = 'categoria'")
	public List<DtoXCategoria> findAll();*/

	@Transactional
	@Modifying
	@Query("DELETE FROM Promocion p WHERE p.idPromocion = ?1")
	public void deletePromocion(Long idPromocion);
	
	
	
}
