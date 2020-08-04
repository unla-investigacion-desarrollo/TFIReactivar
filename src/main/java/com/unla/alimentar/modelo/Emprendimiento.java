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
@Table(name = "emprendimiento")
public class Emprendimiento {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private long idEmprendimiento;
	private String nombre;
	private String cuit;
	private String usuarioModi;
	private Date fechaModi;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipoEmprendimiento", nullable = false)
	@JsonBackReference
	private TipoEmprendimiento tipoEmprendimiento;
	
	@OneToOne
	@MapsId("idUbicacion")
	private Ubicacion ubicacion;
	
	//private IPersona persona;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idRubro", nullable = false)
	@JsonBackReference
	private Rubro rubro;
	
	@OneToMany(mappedBy = "emprendimiento")
	@JsonManagedReference
	private List<Promocion> promociones;
	
	
	@OneToMany(mappedBy = "emprendimiento")
	@JsonManagedReference
	private List<Carrito> carrito;

	@OneToMany(mappedBy = "emprendimiento", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Turno> turnos;
	
	@OneToMany(mappedBy = "emprendimiento")
	@JsonManagedReference
	private List<Articulo> articulos;
	
	@OneToMany(mappedBy = "emprendimiento")
	@JsonManagedReference
	private List<ConfiguracionLocal> configuracionLocales;

	@OneToMany(mappedBy = "emprendimiento", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<OcupacionLocal> ocupacionLocales;
}
