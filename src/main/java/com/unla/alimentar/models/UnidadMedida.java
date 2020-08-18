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
@Table(name = "unidadMedida")
public class UnidadMedida {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy ="uuid")
	private long idUnidadMedida;
	private String nombre;

	@OneToMany(mappedBy="unidadMedida")
	@JsonManagedReference
	private List<ArticuloReferencia> articulosReferencia;
	
	@OneToMany(mappedBy="unidadMedida")
	@JsonManagedReference
	private List<Articulo> articulos;
}
