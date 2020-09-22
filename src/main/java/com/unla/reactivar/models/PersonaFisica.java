package com.unla.reactivar.models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity(name = "personaFisica")
@DiscriminatorValue("fisica")
public class PersonaFisica extends Persona {

	private String nombre;
	private String apellido;
	@Column(unique = true)
	private long dni;
	private String numeroTramite;
	@Column(unique = true)
	private String cuil;
	private String sexo;

}
