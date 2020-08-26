package com.unla.reactivar.models;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity(name = "personaJuridica")
@DiscriminatorValue("juridica")
public class PersonaJuridica extends Persona {

	private String razonSocial;
	private String cuit;

	@ManyToMany(mappedBy = "personasJuridicas")
	private List<Persona> personas;
	
}
