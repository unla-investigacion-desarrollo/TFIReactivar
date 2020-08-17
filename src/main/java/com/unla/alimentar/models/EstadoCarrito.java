package com.unla.alimentar.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "estadoCarrito")
public class EstadoCarrito {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private long idEstadoCarrito;
	private String nombre;
	
	@OneToMany(mappedBy="estadoCarrito")
	@JsonManagedReference
	private List<Carrito> carrito;

}
