package com.unla.alimentar.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.models.Turno;
import com.unla.alimentar.repositories.TurnoRepository;
import com.unla.alimentar.vo.TurnoVo;

@Service
@Transactional(readOnly = true)
public class TurnoService {

	@Autowired
	private TurnoRepository repository;

	public Turno traerTurnoPorId(Long id) {
		return repository.findByIdTurno(id);
	}

	public List<Turno> traerTodos() {
		return repository.findAll();
	}

	@Transactional
	public void borrarTurno(long id) {
		Turno registro = repository.findByIdTurno(id);

		if (registro == null) {
			throw new ObjectNotFound("Turno");
		}

		repository.delete(registro);
	}

	public Turno actualizarTurno(Long id, TurnoVo turnoVo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Turno crearTurno(TurnoVo turnoVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
