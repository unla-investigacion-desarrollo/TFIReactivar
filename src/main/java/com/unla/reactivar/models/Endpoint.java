package com.unla.reactivar.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "endpoint")
public class Endpoint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idEndpoint;
	private String nombreController;
	private String metodo;
	private String endpoint;
	private String descripcion;

	@OneToOne
	@JoinColumn(name = "idFuncion")
	private Funcion funcion;

}
