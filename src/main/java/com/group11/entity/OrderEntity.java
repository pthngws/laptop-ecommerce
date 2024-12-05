package com.group11.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
@Inheritance(strategy = InheritanceType.JOINED)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonManagedReference
    private UserEntity user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "address_id")
    private AddressEntity shippingAddress;

    @Column(name = "order_date" )
    private LocalDateTime orderDate;

    @Column(name = "receive_date")
    private LocalDateTime  receiveDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_status")
    //Trạng thái giao hàng
    private OrderShippingStatus shippingStatus;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String note;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    //Trạng thái giao hàng
    private PaymentStatus paymentStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<LineItemEntity> listLineItems;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    @JsonManagedReference
    private PaymentEntity payment;
}

