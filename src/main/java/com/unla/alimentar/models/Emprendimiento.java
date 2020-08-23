package com.unla.alimentar.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "emprendimiento")
public class Emprendimiento {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idEmprendimiento;
	private String nombre;
	private String cuit;
	private String usuarioModi;
	private Date fechaModi;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "idTipoEmprendimiento", nullable = false)
	@JsonManagedReference
	private TipoEmprendimiento tipoEmprendimiento;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "idUbicacion", nullable = false)
	private Ubicacion ubicacion;
	
	//private IPersona persona;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idRubro", nullable = false)
	@JsonManagedReference
	private Rubro rubro;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPersona", nullable = false)
	@JsonManagedReference
	private Persona persona;
	
	@OneToMany(mappedBy = "emprendimiento")
	@JsonBackReference
	private List<Promocion> promociones;
	
	@OneToMany(mappedBy = "emprendimiento")
	@JsonBackReference
	private List<Carrito> carrito;

	@OneToMany(mappedBy = "emprendimiento", cascade = CascadeType.ALL)
	@JsonBackReference
	private List<Turno> turnos;
	
	@OneToMany(mappedBy = "emprendimiento")
	@JsonBackReference
	private List<Articulo> articulos;
	
	@OneToMany(mappedBy = "emprendimiento", cascade=CascadeType.ALL)
	@JsonBackReference
	private List<ConfiguracionLocal> configuracionLocales = new ArrayList<>();

	@OneToMany(mappedBy = "emprendimiento", cascade = CascadeType.ALL)
	@JsonBackReference
	private List<OcupacionLocal> ocupacionLocales;
}