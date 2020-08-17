package com.unla.alimentar.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "login")
public class Login {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idLogin;
	private String emailString;
	private String clave;

}
