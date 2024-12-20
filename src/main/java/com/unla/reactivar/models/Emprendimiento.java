package com.unla.reactivar.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "emprendimiento")
public class Emprendimiento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idEmprendimiento;
	private String nombre;
	@Column(unique = true)
	private String cuit;
	private String usuarioModi;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fechaModi;
	private int capacidad;
	private boolean aceptaFoto;
	private long telefono;
	
	@ManyToOne
	@JoinColumn(name = "idEstadoEmprendimiento", nullable = false)
	@JsonManagedReference
	private EstadoEmprendimiento estadoEmprendimiento;

	@ManyToOne
	@JoinColumn(name = "idTipoEmprendimiento", nullable = false)
	@JsonManagedReference
	private TipoEmprendimiento tipoEmprendimiento;

	@OneToOne
	@JoinColumn(name = "idUbicacion", nullable = false)
	private Ubicacion ubicacion;

	@ManyToOne
	@JoinColumn(name = "idRubro", nullable = false)
	@JsonManagedReference
	private Rubro rubro;

	@ManyToOne
	@JoinColumn(name = "idPersona", nullable = false)
	@JsonManagedReference
	private Persona persona;

	@OneToMany(mappedBy = "emprendimiento")
	@JsonBackReference
	private List<Promocion> promociones;

	@OneToMany(mappedBy = "emprendimiento")
	@JsonBackReference
	private List<Carrito> carrito;

	@OneToMany(mappedBy = "emprendimiento")
	@JsonBackReference
	private List<Turno> turnos;

	@OneToMany(mappedBy = "emprendimiento")
	@JsonBackReference
	private List<Articulo> articulos;

	@OneToMany(mappedBy = "emprendimiento", cascade = CascadeType.PERSIST)
	@JsonManagedReference
	private List<ConfiguracionLocal> configuracionLocales = new ArrayList<>();

	@OneToMany(mappedBy = "emprendimiento")
	@JsonBackReference
	private List<OcupacionLocal> ocupacionLocales;
	
	@OneToMany(mappedBy = "emprendimiento")
	@JsonManagedReference
	private List<Image> imagenes;
}
