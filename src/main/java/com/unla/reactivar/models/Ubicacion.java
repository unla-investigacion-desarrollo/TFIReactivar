package com.unla.reactivar.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "ubicacion")
public class Ubicacion {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idUbicacion;
	private String calleNumero;
	private int piso;
	private String departamento;
	private String latitud;
	private String longitud;
	private String usuarioModi;
	private Date fechaModi;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idLocalidad", nullable = false)
	@JsonManagedReference
	private Localidad localidad;
	
	@OneToOne(mappedBy = "ubicacion", cascade = CascadeType.ALL)
	@JsonIgnore
	private Emprendimiento emprendimiento;
	
	@OneToOne(mappedBy = "ubicacion", cascade = CascadeType.ALL)
	@JsonIgnore
	private Persona persona;
	
}