package com.unla.reactivar.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "articuloReferencia")
public class ArticuloReferencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idArticuloReferencia;
	@Column(unique = true)
	private String codBarra;
	private String descripcion;
	private String nombre;
	private String foto;
	private double precioRefencia;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fechaModi;
	private String usuarioModi;
	private String peso;

	@ManyToOne
	@JoinColumn(name = "idCategoria", nullable = false)
	@JsonManagedReference
	private Categoria categoria;

	@ManyToOne
	@JoinColumn(name = "idMarca", nullable = false)
	@JsonManagedReference
	private Marca marca;

	@ManyToOne
	@JoinColumn(name = "idUnidadMedida", nullable = false)
	@JsonManagedReference
	private UnidadMedida unidadMedida;

}
