package com.unla.reactivar.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name ="marca")
public class Marca {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idMarca;
	@Column(unique = true)
	private String nombre;
	
	@OneToMany(mappedBy="marca")
	@JsonBackReference
	private List<ArticuloReferencia> articulosReferencia;
	
	@OneToMany(mappedBy="marca")
	@JsonBackReference
	private List<Articulo> articulos;



}
