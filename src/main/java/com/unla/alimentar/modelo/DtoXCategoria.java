package com.unla.alimentar.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name ="dtoXCategoria")
public class DtoXCategoria extends Promocion{

	private double descuento;
	
	//@OneToOne(mappedBy = "idPromocion", cascade = CascadeType.ALL)
	//private Categoria categoria;
}
