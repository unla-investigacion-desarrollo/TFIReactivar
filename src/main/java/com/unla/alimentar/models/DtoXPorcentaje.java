package com.unla.alimentar.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@DiscriminatorValue("porcentaje")
public class DtoXPorcentaje extends Promocion  {

	private double descuento;
}
