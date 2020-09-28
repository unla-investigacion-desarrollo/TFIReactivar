package com.unla.reactivar.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.models.Endpoint;
import com.unla.reactivar.models.Funcion;
import com.unla.reactivar.repositories.EndpointRepository;
import com.unla.reactivar.vo.EndpointVo;

@Service
@Transactional(readOnly = true)
public class EndpointService {

	@Autowired
	private EndpointRepository repository;

	@Autowired
	private FuncionService funcionService;

	public Endpoint traerEndpointPorId(Long id) {
		return repository.findByIdEndpoint(id);
	}

	public List<Endpoint> traerTodosEndpoints() {
		return repository.findAll();
	}

	@Transactional
	public Endpoint crearEndpoint(EndpointVo endpointVo) {
		Endpoint endpoint = new Endpoint();

		adaptVoToEndpoint(endpoint, endpointVo);

		try {
			endpoint = repository.save(endpoint);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return endpoint;
	}

	@Transactional
	public void borrarEndpoint(long id) {
		Endpoint registro = repository.findByIdEndpoint(id);

		if (registro == null) {
			throw new ObjectNotFound("Endpoint");
		}

		repository.delete(registro);
	}

	@Transactional
	public Endpoint actualizarEndpoint(Long id, EndpointVo endpointVo) {
		Endpoint endpoint = repository.findByIdEndpoint(id);

		if (endpoint == null) {
			throw new ObjectNotFound("Endpoint");
		}

		adaptPutVoToEndpoint(endpoint, endpointVo);

		try {
			endpoint = repository.save(endpoint);
		} catch (Exception e) {
			if (e.getCause() != null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new ObjectAlreadyExists();
			}
		}

		return endpoint;
	}

	private void adaptVoToEndpoint(Endpoint endpoint, EndpointVo endpointVo) {
		Funcion funcion = funcionService.crearFuncion(endpointVo.getFuncion());
		endpoint.setDescripcion(endpointVo.getDescripcion());
		endpoint.setEndpoint(endpointVo.getEndpoint());
		endpoint.setMetodo(endpointVo.getMetodo());
		endpoint.setNombreController(endpointVo.getNombreController());
		endpoint.setFuncion(funcion);

	}

	private void adaptPutVoToEndpoint(Endpoint endpoint, EndpointVo endpointVo) {
		Funcion funcion = funcionService.actualizarFuncion(endpoint.getFuncion().getIdFuncion(),
				endpointVo.getFuncion());

		endpoint.setDescripcion(endpointVo.getDescripcion());
		endpoint.setEndpoint(endpointVo.getEndpoint());
		endpoint.setMetodo(endpointVo.getMetodo());
		endpoint.setNombreController(endpointVo.getNombreController());
		endpoint.setFuncion(funcion);

	}

}
