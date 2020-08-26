package com.unla.reactivar.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "estadoCarrito")
public class EstadoCarrito {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idEstadoCarrito;
	private String nombre;
	
	@OneToMany(mappedBy="estadoCarrito")
	@JsonBackReference
	private List<Carrito> carrito;

}
