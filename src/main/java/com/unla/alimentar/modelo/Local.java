/**
 * 
 */
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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

/**
 * @author Matias
 *
 */
@Data
@Entity
@Table(name = "local")
public class Local {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String idLocal;
	private String nombre;
	private int cuit;
	private String calle;
	private int numeracion;
	private int codigoPostal;
	private String departamento;
	private String latitud;
	private String longitud;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idLocalidad", nullable = false)
	@JsonBackReference
	private Localidad localidad;

	private String usuarioModi;
	private Date fechaModi;
	private int capacidad;
	private boolean usaTurno;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idRubro", nullable = false)
	@JsonBackReference
	private Rubro rubro;

	@OneToOne
	@MapsId("idUsuario")
	private Usuario usuario;

	@OneToMany(mappedBy = "local", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<OcupacionLocal> ocupacionLocales;

	@OneToMany(mappedBy = "local", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Turno> turnos;

	@OneToOne(mappedBy = "local", cascade = CascadeType.ALL)
	private ConfiguracionTurno configuracionTurno;
}
