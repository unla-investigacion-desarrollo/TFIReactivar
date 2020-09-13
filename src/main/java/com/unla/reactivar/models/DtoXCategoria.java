package com.unla.reactivar.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@DiscriminatorValue("categoria")
public class DtoXCategoria extends Promocion {

	private double descuento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCategoria", nullable = true)
	@JsonManagedReference
	private Categoria categoria;

}
