package com.epam.esm.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long id;

    @Column(name = "order_purchaseTime")
    private LocalDateTime purchaseTime;

    @Column(name = "order_price")
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name="gift_certificate_id", nullable=false)
    private GiftCertificate giftCertificate;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User owner;
}