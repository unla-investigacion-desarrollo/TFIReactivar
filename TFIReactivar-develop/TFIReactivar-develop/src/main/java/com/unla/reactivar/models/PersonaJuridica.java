package com.unla.reactivar.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity(name = "personaJuridica")
@DiscriminatorValue("juridica")
public class PersonaJuridica extends Persona {

	private String razonSocial;
	@Column(unique = true)
	private String cuit;

	@ManyToMany(mappedBy = "personasJuridicas")
	private List<Persona> personas;
	
}
