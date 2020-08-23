package com.unla.alimentar.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
@DiscriminatorValue("llevapaga")
public class LlevaPaga extends Promocion {

	private int lleva;
	private double paga;
}
