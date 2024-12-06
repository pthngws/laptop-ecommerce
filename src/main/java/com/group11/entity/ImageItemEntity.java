package com.group11.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "image_items")
public class ImageItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "image_url",columnDefinition = "text", nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "product_detail_id", nullable = false)
    @JsonBackReference
    private ProductDetailEntity productDetail;

}