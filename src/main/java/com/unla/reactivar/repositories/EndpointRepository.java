package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.Endpoint;

@Repository
public interface EndpointRepository extends JpaRepository<Endpoint, Long> {

	public Endpoint findByIdEndpoint(Long idEndpoint);

}
