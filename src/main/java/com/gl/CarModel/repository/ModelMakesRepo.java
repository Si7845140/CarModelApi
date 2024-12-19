package com.gl.CarModel.repository;

import com.gl.CarModel.entity.ModelMakes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelMakesRepo extends JpaRepository <ModelMakes,Long> {
}
