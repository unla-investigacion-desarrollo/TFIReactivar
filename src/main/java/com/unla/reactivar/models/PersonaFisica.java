package com.unla.reactivar.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;

@Data
@Entity(name = "personaFisica")
@DiscriminatorValue("fisica")
public class PersonaFisica extends Persona {
	
	private String nombre;
	private String apellido;
	private String cuil; 
	
}
