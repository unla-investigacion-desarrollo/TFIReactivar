package com.unla.reactivar.task;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.unla.reactivar.models.OcupacionLocal;
import com.unla.reactivar.services.OcupacionLocalService;
import com.unla.reactivar.utils.DateUtils;

@Service
public class OcupacionLocalTask {
	
	private final Logger log = LoggerFactory.getLogger(getClass().getName());
	
	@Autowired
	private OcupacionLocalService service;
	
	private static final int DIEZ_MINUTOS = 600000;
	private static final int CUARENTA_MINUTOS = 2400000;
	
	@Scheduled(fixedRate = DIEZ_MINUTOS)
	public void completarSalidasNoMarcadas() {
		log.info("Comienza tarea - Verificamos Ocupaciones No Marcadas");
		List<OcupacionLocal> ocupacionesLocal = service.traerOcupacionesSinSalida();

		if (!ocupacionesLocal.isEmpty()) {
			for (OcupacionLocal ocupacion : ocupacionesLocal) {
				Date fechaHoraEntrada = ocupacion.getFechaHoraEntrada();
				if(DateUtils.fechaHoy().getTime() > fechaHoraEntrada.getTime() + CUARENTA_MINUTOS) {
					ocupacion.setFechaHoraSalida(new Date(ocupacion.getFechaHoraEntrada().getTime() + DIEZ_MINUTOS));
					service.actualizarFechaSalidaVacia(ocupacion);
				}
			}
		}
	}

}
