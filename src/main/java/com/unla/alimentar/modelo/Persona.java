package com.unla.alimentar.modelo;

import java.util.Date;
import java.util.List;

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

@Data
@Entity
@Table(name = "persona")
public class Persona {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private long idPersona;

	private String celular;
	private String usuarioModi;
	private Date fechaModi;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPerfil", nullable = false)
	@JsonBackReference
	private Perfil perfil;
	
	@OneToOne
	@MapsId("idUbicacion")
	private Ubicacion ubicacion;
	
	@OneToOne
	@MapsId("idLogin")
	private Login login;
	
	@OneToMany(mappedBy = "persona")
	@JsonManagedReference
	private List<Turno> turnos;
	
	@OneToMany(mappedBy = "persona")
	@JsonManagedReference
	private List<OcupacionLocal> ocupacionLocales;
	
	@OneToMany(mappedBy = "persona")
	@JsonManagedReference
	private List<Carrito> carritos;
}
