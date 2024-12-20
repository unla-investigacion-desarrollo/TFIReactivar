package com.unla.reactivar.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "itemCarrito")
public class ItemCarrito {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idItemCarrito;

	@ManyToOne
	@JoinColumn(name = "idArticulo", nullable = false)
	@JsonManagedReference
	private Articulo articuloPrecio;

	private int cantidad;

	@ManyToOne
	@JoinColumn(name = "idCarrito", nullable = false)
	@JsonManagedReference
	private Carrito carrito;

}
