package com.unla.alimentar.modelo;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "dtoXPorcentaje")
public class DtoXPorcentaje extends Promocion  {

	private double descuento;
}
