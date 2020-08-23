package com.unla.alimentar.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity(name = "personaFisica")
@DiscriminatorValue("fisica")
public class PersonaFisica extends Persona {
	
	private String nombre;
	private String apellido;
	private String cuil; 
	
}
