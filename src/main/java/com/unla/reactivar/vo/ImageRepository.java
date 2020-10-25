package com.unla.reactivar.vo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.reactivar.models.Emprendimiento;
import com.unla.reactivar.models.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{

	public Image findByIdImage(long idImagen);

	public List<Image> findByEmprendimiento(Emprendimiento emprendimiento);

}
