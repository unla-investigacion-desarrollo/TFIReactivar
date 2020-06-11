package com.unla.alimentar.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "localidad")
public class Localidad {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String idLocalidad;
	private String nombre;
	private Provincia provincia;
	
	@OneToMany(mappedBy = "rubro", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Local> locales;
}
