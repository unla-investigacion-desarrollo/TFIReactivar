package com.unla.alimentar.modelo;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "carrito")
public class Carrito {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private long idCarrito;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmprendimiento", nullable = false)
	@JsonBackReference
	private Emprendimiento emprendimiento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPersona", nullable = false)
	@JsonBackReference
	private Persona persona;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idItemCarrito",nullable= false)
	@JsonBackReference
	private ItemCarrito listaItemCarrito;
	
	private Date fechaHora;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idEstadoCarrito",nullable= false)
	@JsonBackReference
	private EstadoCarrito estadoCarrito;
	
	
	

}
