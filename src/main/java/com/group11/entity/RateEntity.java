package com.group11.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rates")
public class RateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id")
    private Long rateID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference
    private ProductEntity product;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private int rate;

    @Column(columnDefinition = "TEXT")
    private String response;  // Trường phản hồi của Admin

    @OneToMany(mappedBy = "rate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<MediaRateEntity> images;

}
