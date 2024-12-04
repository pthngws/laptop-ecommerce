package com.group11.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "image_rates")
public class ImageRateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "image_url",columnDefinition = "text", nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "rate_id", nullable = false)
    @JsonManagedReference
    private RateEntity rate;

}