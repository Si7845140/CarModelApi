package com.gl.CarModel.repository;

import com.gl.CarModel.entity.CarModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarModelsRepo extends JpaRepository<CarModels,Long> {
    List<CarModels> findByYear(Integer year);

    @Query("SELECT c FROM CarModels c WHERE c.year = :year AND c.makes_id = :makes_id")
    List<CarModels> findByYearAndMakesId(@Param("year") Integer year, @Param("makes_id") String makes_id);

    @Query("SELECT c FROM CarModels c WHERE c.year = :year AND c.makes_id = :makes_id AND c.model_name = :model_name")
    CarModels findByYearAndMakesIdAndModelName(@Param("year") Integer year,
                                               @Param("makes_id") String makes_id,
                                               @Param("model_name") String model_name);
}
