package com.unla.alimentar.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name ="dtoXCategoria")
public class DtoXCategoria extends Promocion{

	private double descuento;
	
	@OneToOne
	@MapsId("idCategoria")
	private Categoria categoria;
}