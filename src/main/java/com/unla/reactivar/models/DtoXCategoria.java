package com.unla.reactivar.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
@DiscriminatorValue("categoria")
public class DtoXCategoria extends Promocion {

	private double descuento;

	@OneToOne
	@JoinColumn(name = "idCategoria", nullable = true)
	private Categoria categoria;
}
