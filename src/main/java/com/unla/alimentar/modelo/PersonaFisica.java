package com.unla.alimentar.modelo;

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
	
	  /*@ManyToMany(cascade = CascadeType.ALL)
	    private List<PersonaJuridica> personasJuridicas;*/
	
	
}
