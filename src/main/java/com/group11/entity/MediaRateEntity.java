package com.group11.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "media_rates")
public class MediaRateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id")
    private Long id;

    @Column(name = "media_url",columnDefinition = "text", nullable = false)
    private String mediaUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", nullable = false)
    private MediaRateType mediaType;

    @ManyToOne
    @JoinColumn(name = "rate_id", nullable = false)
    @JsonManagedReference
    private RateEntity rate;

}