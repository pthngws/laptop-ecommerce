package com.group11.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty
    private String RAM;

    @Column(columnDefinition = "text")
    @JsonProperty
    private String CPU;

    @Column(columnDefinition = "text")
    @JsonProperty
    private String GPU;

    @Column(columnDefinition = "text")
    @JsonProperty
    private String monitor;

    @Column(columnDefinition = "text")
    @JsonProperty
    private String charger;

    @Column(columnDefinition = "text")
    @JsonProperty
    private String disk;

    @Column(columnDefinition = "text")
    @JsonProperty
    private String connect;

    @Column(columnDefinition = "text")
    @JsonProperty
    private String LAN;

    @Column(columnDefinition = "text")
    @JsonProperty
    private String WIFI;

    @Column(columnDefinition = "text")
    @JsonProperty
    private String bluetooth;

    @Column(columnDefinition = "text")
    @JsonProperty
    private String audio;

    @Column(columnDefinition = "text")
    @JsonProperty
    private String webcam;

    @Column(name = "operation_system", columnDefinition = "text")
    @JsonProperty
    private String operationSystem;

    @Column(columnDefinition = "text")
    @JsonProperty
    private String weight;

    @Column(columnDefinition = "text")
    @JsonProperty
    private String color;

    @Column(columnDefinition = "text")
    @JsonProperty
    private String size;

    @Column(columnDefinition = "text")
    @JsonProperty
    private String description;

    @OneToMany(mappedBy = "productDetail", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    @JsonProperty
    private List<ImageItemEntity> images;
}
