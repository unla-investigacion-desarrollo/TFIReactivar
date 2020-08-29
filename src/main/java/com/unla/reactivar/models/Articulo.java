package com.unla.reactivar.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "articulo")
public class Articulo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idArticulo;
	private String codBarra;
	private String descripcion;
	private String nombre;
	private String foto;
	private double precio;
	private Date fechaModi;
	private String usuarioModi;
	private String peso;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCategoria", nullable = false)
    @JsonManagedReference
	private Categoria categoria;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idMarca", nullable = false)
    @JsonManagedReference
	private Marca marca;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUnidadMedida", nullable = false)
    @JsonManagedReference
	private UnidadMedida unidadMedida;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmprendimiento", nullable = true)
    @JsonManagedReference
	private Emprendimiento emprendimiento;

	private boolean activoComercial;
	private boolean visible;

	@OneToOne(mappedBy = "articuloPrecio", cascade = CascadeType.ALL)
    @JsonManagedReference
	private ItemCarrito itemCarrito;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPromocion", nullable = true)
    @JsonManagedReference
	private Promocion promocion;

}
