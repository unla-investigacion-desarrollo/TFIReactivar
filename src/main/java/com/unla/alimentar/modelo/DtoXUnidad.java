package com.unla.alimentar.modelo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@DiscriminatorValue("unidad")
public class DtoXUnidad extends Promocion  {
	
	private double porcDescuento;
	private int cantidad;
}
