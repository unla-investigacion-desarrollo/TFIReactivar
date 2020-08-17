package com.unla.alimentar.models;

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
@Table(name ="articuloRefencia")
public class ArticuloReferencia {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private long idArticuloReferencia ;
	private String codBarra;
	private String descripcion;
	private String nombre;
	private String foto;
	private double precioRefencia;
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

}
