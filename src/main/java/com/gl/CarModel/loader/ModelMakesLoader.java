package com.gl.CarModel.loader;

import com.gl.CarModel.entity.ModelColors;
import com.gl.CarModel.entity.ModelMakes;
import com.gl.CarModel.repository.ModelColorsRepo;
import com.gl.CarModel.repository.ModelMakesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ModelMakesLoader implements CommandLineRunner {
    @Autowired
    ModelMakesRepo modelMakesRepo;

    @Override
    public void run(String... args) throws Exception {


        List<ModelMakes> makes = modelMakesRepo.findAll();

        if (makes.isEmpty()) {
            System.out.println("No data was found in the database.The added datas are");
            ModelMakes modelMakes1 = new ModelMakes();
            modelMakes1.setMakes("Mahindra");
            ModelMakes modelMakes2 = new ModelMakes();
            modelMakes2.setMakes("Lincoln");
            modelMakesRepo.save(modelMakes1);
            modelMakesRepo.save(modelMakes2);
            makes=modelMakesRepo.findAll();
            System.out.println("Loaded ModelMakes data: " + makes);

        } else {
            System.out.println("Loaded ModelMakes data: " + makes);
        }
    }

}
