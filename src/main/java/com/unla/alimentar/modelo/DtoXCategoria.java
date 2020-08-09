package com.unla.alimentar.modelo;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@DiscriminatorValue("categoria")
public class DtoXCategoria extends Promocion{

	private double descuento;
	
	@OneToOne
	@MapsId("idCategoria")
	private Categoria categoria;
}
