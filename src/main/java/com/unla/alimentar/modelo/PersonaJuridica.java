package com.unla.alimentar.modelo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "personaJuridica")
public class PersonaJuridica extends Persona{

	private String razonSocial;
	private String cuit; 
	
	@ManyToMany(mappedBy = "personasJuridicas")
	    private List<Persona> personas;
	/*@ManyToMany(cascade = CascadeType.ALL)
	    private List<PersonaFisica> personasFisicas;*/
	
	/*@ManyToMany(fetch= FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(name="personaFisicaJuridica")
	private List<PersonaFisica> personasFisicas;*/
}
