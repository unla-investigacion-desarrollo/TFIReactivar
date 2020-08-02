package com.unla.alimentar.modelo;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "promocion")
public class Promocion {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private long idPromocion ;
	private Date fechaInicio;
	private Date fechaFin;
	private boolean habilitada;
	private String descripcion;
	//private Emprendimiento emprendimiento;
	//private List<Articulo> lstArticulos;
	

}
