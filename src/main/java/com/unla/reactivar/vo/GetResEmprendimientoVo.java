package com.unla.reactivar.vo;

import java.util.Date;

import com.unla.reactivar.models.EstadoEmprendimiento;
import com.unla.reactivar.models.Persona;
import com.unla.reactivar.models.Rubro;
import com.unla.reactivar.models.TipoEmprendimiento;
import com.unla.reactivar.models.Ubicacion;
import lombok.Data;

@Data
public class GetResEmprendimientoVo {

	private long idEmprendimiento;
	private String nombre;
	private String cuit;
	private String usuarioModi;
	private Date fechaModi;
	private int capacidad;
	private boolean aceptaFoto;
	private EstadoEmprendimiento estadoEmprendimiento;
	private TipoEmprendimiento tipoEmprendimiento;
	private Ubicacion ubicacion;
	private Rubro rubro;

	private boolean usaTurnos;
	private int nroColor;
	private int cantPersonasEnLocal;

}
