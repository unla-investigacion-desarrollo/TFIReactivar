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
@Table(name = "unidadMedida")
public class UnidadMedida {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idUnidadMedida;
	@Column(unique = true)
	private String nombre;

	@OneToMany(mappedBy="unidadMedida")
	@JsonBackReference
	private List<ArticuloReferencia> articulosReferencia;
	
	@OneToMany(mappedBy="unidadMedida")
	@JsonBackReference
	private List<ReqArticulo> articulos;
}
