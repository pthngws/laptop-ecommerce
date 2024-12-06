package com.group11.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="productDetails" )
public class ProductDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long productDetailID;

    @Column(columnDefinition = "text")
    private String RAM;

    @Column(columnDefinition = "text")
    private String CPU;

    @Column(columnDefinition = "text")
    private String GPU;

    @Column(columnDefinition = "text")
    private String monitor;

    @Column(columnDefinition = "text")
    private String charger;

    @Column(columnDefinition = "text")
    private String disk;

    @Column(columnDefinition = "text")
    private String connect;

    @Column(columnDefinition = "text")
    private String LAN;

    @Column(columnDefinition = "text")
    private String WIFI;

    @Column(columnDefinition = "text")
    private String bluetooth;

    @Column(columnDefinition = "text")
    private String audio;

    @Column(columnDefinition = "text")
    private String webcam;

    @Column(name = "operation_system", columnDefinition = "text")
    private String operationSystem;

    @Column(columnDefinition = "text")
    private String weight;

    @Column(columnDefinition = "text")
    private String color;

    @Column(columnDefinition = "text")
    private String size;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "productDetail", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ImageItemEntity> images;
}
