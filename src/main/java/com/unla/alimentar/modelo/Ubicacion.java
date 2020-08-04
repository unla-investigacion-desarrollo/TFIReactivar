package com.unla.alimentar.modelo;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "ubicacion")
public class Ubicacion {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private long idUbicacion;
	private String calle;
	private int numero;
	private int piso;
	private String departamento;
	private String latitud;
	private String longitud;
	private String usuarioModi;
	private Date fechaModi;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idLocalidad", nullable = false)
	@JsonBackReference
	private Localidad localidad;
	
	@OneToOne(mappedBy = "ubicacion", cascade = CascadeType.ALL)
	@JsonIgnore
	private Emprendimiento emprendimiento;
	
	@OneToOne(mappedBy = "ubicacion", cascade = CascadeType.ALL)
	@JsonIgnore
	private Persona persona;
	
}
