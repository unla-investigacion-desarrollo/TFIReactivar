package com.unla.reactivar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.ResetAndValidatingToken;

@Repository
public interface ResetAndValidatingTokenRepository extends JpaRepository<ResetAndValidatingToken, Long> {

	public ResetAndValidatingToken findByToken(String token);

}
