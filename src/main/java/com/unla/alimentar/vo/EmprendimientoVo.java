package com.unla.alimentar.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class EmprendimientoVo {
	
	private String nombre;
	private String cuit;
	private String usuarioModi;
	private Date fechaModi;
	private long idTipoEmprendimiento;
	private UbicacionVo ubicacionVo;
	private long idRubro;
	private long idPersona;
	private List<ConfiguracionLocalVo> configuracionLocales;

}
