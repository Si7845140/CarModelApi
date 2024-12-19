package com.gl.CarModel.repository;

import com.gl.CarModel.entity.ModelColors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelColorsRepo extends JpaRepository<ModelColors,Long> {
}
