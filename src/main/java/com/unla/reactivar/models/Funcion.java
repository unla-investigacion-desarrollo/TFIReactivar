package com.unla.reactivar.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "funcion")
public class Funcion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idFuncion;
	@Column(unique = true)
	private String descripcion;
	
	@OneToMany(mappedBy = "funcion")
	@JsonBackReference
	private List<FuncionPerfil> funcionesPerfil;
	
}
