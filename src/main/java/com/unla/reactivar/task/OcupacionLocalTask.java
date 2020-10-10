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

	private static final int CINCO_MINUTOS = 300000;

	@Scheduled(fixedRate = CINCO_MINUTOS)
	public void completarSalidasNoMarcadas() {
		log.info("Comienza tarea - Verificamos Ocupaciones No Marcadas");
		verSalidas();
	}

	private void verSalidas() {
		List<OcupacionLocal> ocupacionesLocal = service.traerOcupacionesSinSalida();

		if (!ocupacionesLocal.isEmpty()) {
			for (OcupacionLocal ocupacion : ocupacionesLocal) {
				Date fechaHoraEntrada = ocupacion.getFechaHoraEntrada();
				int tiempoAtencion = ocupacion.getEmprendimiento().getConfiguracionLocales().get(0).getTiempoAtencion();
				if (DateUtils.fechaHoy().getTime() > fechaHoraEntrada.getTime() + tiempoAtencion) {
					ocupacion.setFechaHoraSalida(new Date(ocupacion.getFechaHoraEntrada().getTime() + tiempoAtencion * 60000));
					service.actualizarFechaSalidaVacia(ocupacion);
				}
			}
		}
	}

}
