package com.unla.alimentar.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "localidad")
public class Localidad {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idLocalidad;
	private String nombre;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idProvincia", nullable = false)
	@JsonBackReference
	private Provincia provincia;


	/*@OneToMany(mappedBy = "localidad")
	@JsonManagedReference
	private List<Usuario> usuarios;*/

	
	
	@OneToMany(mappedBy = "localidad")
	@JsonManagedReference
	private List<Ubicacion> ubicaciones;

	
	/*@OneToMany(mappedBy = "localidad")
	@JsonManagedReference
	private List<Local> locales;*/


}
