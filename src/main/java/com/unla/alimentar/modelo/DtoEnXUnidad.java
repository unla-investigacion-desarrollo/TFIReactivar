package com.unla.alimentar.modelo;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "dtoEnXUnidad")
public class DtoEnXUnidad extends Promocion  {
	
	private double porcDescuento;
	private int cantidad;
}
