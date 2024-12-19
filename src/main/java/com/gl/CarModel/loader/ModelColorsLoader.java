package com.gl.CarModel.loader;

import com.gl.CarModel.entity.ModelColors;
import com.gl.CarModel.repository.ModelColorsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModelColorsLoader implements CommandLineRunner {
    @Autowired
    ModelColorsRepo modelColorsRepo;

    @Override
    public void run(String... args) throws Exception {

        List<ModelColors> colors = modelColorsRepo.findAll();

        if (colors.isEmpty()) {
            System.out.println("No was data found in the database.The new Added datas");

            ModelColors modelColors1 = new ModelColors();
            modelColors1.setColor("CHARCOAL");
            ModelColors modelColors2 = new ModelColors();
            modelColors2.setColor("DIAMOND");
            modelColorsRepo.save(modelColors1);
            modelColorsRepo.save(modelColors2);
            colors=modelColorsRepo.findAll();
            System.out.println("Loaded ModelColors data: " + colors);


        } else {

            System.out.println("Loaded ModelColors data: " + colors);
        }
    }

}
