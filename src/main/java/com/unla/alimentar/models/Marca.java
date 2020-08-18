package com.unla.alimentar.models;

import java.util.List;

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
@Table(name ="marca")
public class Marca {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private long idMarca ;
	private String nombre;
	
	@OneToMany(mappedBy="marca")
	@JsonManagedReference
	private List<ArticuloReferencia> articulosReferencia;
	
	@OneToMany(mappedBy="marca")
	@JsonManagedReference
	private List<Articulo> articulos;



}
