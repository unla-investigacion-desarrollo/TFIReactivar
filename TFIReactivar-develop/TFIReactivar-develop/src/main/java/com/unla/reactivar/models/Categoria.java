package com.unla.reactivar.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name ="categoria")
public class Categoria {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idCategoria;
	@Column(unique = true)
	private String nombre;

	@OneToMany(mappedBy="categoria")
	@JsonBackReference
	private List<ArticuloReferencia> articulosReferencia;
	
	@OneToMany(mappedBy="categoria")
	@JsonBackReference
	private List<ReqArticulo> articulos;
		
	@OneToOne(mappedBy = "categoria", cascade = CascadeType.ALL)
	@JsonIgnore
	private DtoXCategoria dtoXCategoria;
}
