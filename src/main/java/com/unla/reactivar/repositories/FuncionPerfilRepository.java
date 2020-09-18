package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.FuncionPerfil;

@Repository
public interface FuncionPerfilRepository extends JpaRepository<FuncionPerfil, Long> {

	public FuncionPerfil findByIdFuncionPerfil(Long idFuncionPerfil);

}
