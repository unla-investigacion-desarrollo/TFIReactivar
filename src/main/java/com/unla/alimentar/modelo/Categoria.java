package com.unla.alimentar.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name ="categoria")
public class Categoria {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private long idCategoria ;
	private String nombre;

	@OneToMany(mappedBy="categoria")
	@JsonManagedReference
	private List<ArticuloReferencia> articulosReferencia;
	
	@OneToMany(mappedBy="categoria")
	@JsonManagedReference
	private List<Articulo> articulos;
	
	/*@OneToOne
	@MapsId("idLocal")
	private DtoXCategoria dtoXCategoria;
	*/
}
