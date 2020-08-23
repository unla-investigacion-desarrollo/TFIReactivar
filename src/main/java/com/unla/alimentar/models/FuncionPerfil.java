package com.unla.alimentar.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "funcionPerfil")
public class FuncionPerfil {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idFuncionPerfil;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPerfil", nullable = false)
	@JsonManagedReference
	private Perfil perfil;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idFuncion", nullable = false)
	@JsonManagedReference
	private Funcion funcion;
	
	private boolean edicion;
	private String usuarioModi;
	private Date fechaModi;
	
}
