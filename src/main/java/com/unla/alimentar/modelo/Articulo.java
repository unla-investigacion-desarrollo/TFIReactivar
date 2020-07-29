package com.unla.alimentar.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name ="articulo")
public class Articulo {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
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
	@JoinColumn(name="idCategoria",nullable= false)
	@JsonBackReference
	private Categoria categoria;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idMarca",nullable= false)
	@JsonBackReference
	private Marca marca;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idUnidadMedida",nullable= false)
	@JsonBackReference
	private UnidadMedida unidadMedida;
	
	//private Emprendimiento emprendimiento;
	
	private boolean activoComercial;
	private boolean visible;

}
