package com.unla.reactivar.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "carrito")
public class Carrito {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idCarrito;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmprendimiento", nullable = false)
	@JsonManagedReference
	private Emprendimiento emprendimiento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPersona", nullable = false)
	@JsonManagedReference
	private Persona persona;

	@OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
	@JsonBackReference
	private List<ItemCarrito> listaItemCarrito;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fechaHora;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEstadoCarrito", nullable = false)
	@JsonManagedReference
	private EstadoCarrito estadoCarrito;

}
