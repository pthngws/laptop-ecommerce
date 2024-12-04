package com.group11.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "manufacturers")
public class ManufacturerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manufacturer_id")
    private Long id;

    @Column(name = "name", columnDefinition = "nvarchar(250)", nullable = false)
    private String name;


    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductEntity> products;

}
