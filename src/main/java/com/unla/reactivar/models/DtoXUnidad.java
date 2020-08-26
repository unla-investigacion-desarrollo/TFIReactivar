package com.unla.reactivar.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
@DiscriminatorValue("unidad")
public class DtoXUnidad extends Promocion  {
	
	private double porcDescuento;
	private int cantidad;
}
