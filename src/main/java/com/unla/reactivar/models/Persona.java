package com.unla.reactivar.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoPersona", discriminatorType = DiscriminatorType.STRING)
public abstract class Persona implements Serializable {

	private static final long serialVersionUID = 6151260972153503761L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPersona;

	@Column(nullable = true)
	private String celular;
	@Column(nullable = true)
	private String usuarioModi;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fechaModi;

	@ManyToOne
	@JoinColumn(name = "idPerfil", nullable = true)
	@JsonManagedReference
	private Perfil perfil;

	@OneToOne
	@JoinColumn(name = "idUbicacion", nullable = true)
	private Ubicacion ubicacion;

	@OneToOne
	@JoinColumn(name = "idLogin",nullable = true)
	private Login login;

	@ManyToOne
	@JoinColumn(name = "idEstadoPersona", nullable = true)
	@JsonManagedReference
	private EstadoPersona estadoPersona;

	@OneToMany(mappedBy = "persona")
	@JsonBackReference
	private List<Turno> turnos;

	@OneToMany(mappedBy = "persona")
	@JsonBackReference
	private List<OcupacionLocal> ocupacionLocales;

	@OneToMany(mappedBy = "persona")
	@JsonBackReference
	private List<Carrito> carritos;

	@ManyToMany
	private List<PersonaJuridica> personasJuridicas;

	@OneToMany(mappedBy = "persona")
	@JsonBackReference
	private List<Emprendimiento> emprendimientos;

}
