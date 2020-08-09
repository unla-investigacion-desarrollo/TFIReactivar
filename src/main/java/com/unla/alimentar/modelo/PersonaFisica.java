package com.unla.alimentar.modelo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "personaFisica")
public class PersonaFisica extends Persona {
	private String nombre;
	private String apellido;
	private String cuil; 
	
	  /*@ManyToMany(cascade = CascadeType.ALL)
	    private List<PersonaJuridica> personasJuridicas;*/
	
	
}
