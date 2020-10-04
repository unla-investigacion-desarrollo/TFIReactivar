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
	
	
	/*@Transactional
	public List<Endpoint> crearListEndpoint(List<EndpointVo> endpointVo) {
		List<Endpoint> listEndpoint = new ArrayList();
		Endpoint endpoint = new Endpoint();
		adaptVoToEndpoint(listEndpoint, endpointVo);

		for (int i = 0; i < listEndpoint.size(); i++) {

			endpoint = listEndpoint.get(i);

			try {
				endpoint = repository.save(endpoint);
			} catch (Exception e) {
				if (e.getCause() != null
						&& e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
					throw new ObjectAlreadyExists();
				}
			}

		}
		return listEndpoint;
	}*/

	/*private void adaptVoToEndpoint(List<Endpoint> endpoint, List<EndpointVo> endpointVo) {
		Endpoint endpointNuevo = new Endpoint();

		for (int i = 0; i < endpointVo.size(); i++) {

			Funcion funcion = funcionService.crearFuncion(endpointVo.get(i).getFuncion());
			endpointNuevo.setDescripcion(endpointVo.get(i).getDescripcion());
			endpointNuevo.setEndpoint(endpointVo.get(i).getEndpoint());
			endpointNuevo.setMetodo(endpointVo.get(i).getMetodo());
			endpointNuevo.setNombreController(endpointVo.get(i).getNombreController());
			endpointNuevo.setFuncion(funcion);

			endpoint.add(endpointNuevo);

		}

	}*/

}
