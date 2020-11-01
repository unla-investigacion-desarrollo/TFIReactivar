package com.unla.reactivar.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;
@Data
public class GetResTurnoVo {
	

private long idTurno;
private Date fechaHora;
private String observaciones;
private long idEstadoTurno;
private String estado;
private long idEmprendimiento;
private String nombre;
private String latitud;
private String longitud;
private long telefono;

}
