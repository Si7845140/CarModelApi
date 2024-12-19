//package com.gl.CarModel.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.time.LocalDateTime;
//
//@Data
//@Entity
//@Table(
//        uniqueConstraints = @UniqueConstraint(columnNames = {"makes_id", "model_name","year"})
//)
//
//public class CarModels {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long model_id;
//    private String makes_id;
//    private String model_name;
//    private String color_id;
//    private Integer year;
//
//    @Column(nullable = false,updatable = false)
//    @CreationTimestamp
//    private LocalDateTime createdAt;
//    @Column(nullable = false)
//    @UpdateTimestamp
//    private LocalDateTime updatedAt;
//
//}
package com.gl.CarModel.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"makes_id", "model_name", "year"})
)
public class CarModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long model_id;

    private String makes_id;
    private String model_name;
    private String color_id;
    private Integer year;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
