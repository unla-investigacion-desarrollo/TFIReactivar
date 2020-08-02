package com.unla.alimentar.modelo;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "llevaPaga")
public class LlevaPaga extends Promocion {

	private int lleva;
	private double paga;
}
