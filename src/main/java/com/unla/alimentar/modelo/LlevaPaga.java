package com.unla.alimentar.modelo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@DiscriminatorValue("llevapaga")
public class LlevaPaga extends Promocion {

	private int lleva;
	private double paga;
}
