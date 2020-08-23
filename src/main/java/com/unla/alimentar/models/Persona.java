package com.unla.alimentar.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipoPersona", 
discriminatorType = DiscriminatorType.STRING)
public class Persona {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idPersona;

	private String celular;
	private String usuarioModi;
	private Date fechaModi;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPerfil", nullable = false)
	@JsonManagedReference
	private Perfil perfil;
	
	@OneToOne
	@JoinColumn(name = "idUbicacion", nullable = true)
	private Ubicacion ubicacion;
	
	@OneToOne
    @JoinColumn(name = "idLogin")
	private Login login;
	
	@OneToMany(mappedBy = "persona")
	@JsonBackReference
	private List<Turno> turnos;
	
	@OneToMany(mappedBy = "persona")
	@JsonBackReference
	private List<OcupacionLocal> ocupacionLocales;
	
	@OneToMany(mappedBy = "persona")
	@JsonBackReference
	private List<Carrito> carritos;
	
	@ManyToMany(cascade = CascadeType.ALL)
    private List<PersonaJuridica> personasJuridicas;
	
	@OneToMany(mappedBy = "persona")
	@JsonBackReference
	private List<Emprendimiento> emprendimientos;
}
