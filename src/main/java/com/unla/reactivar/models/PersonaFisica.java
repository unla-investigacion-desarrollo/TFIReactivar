package com.unla.reactivar.models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity(name = "personaFisica")
@DiscriminatorValue("fisica")
public class PersonaFisica extends Persona {

	@Column(nullable = true) 
	private String nombre;
	@Column(nullable = true) 
	private String apellido;
	@Column(unique = true)
	private long dni;
	@Column(nullable = true) 
	private String numeroTramite;
	@Column(nullable = true)
	private String cuil;
	@Column(nullable = true) 
	private String sexo;

}
