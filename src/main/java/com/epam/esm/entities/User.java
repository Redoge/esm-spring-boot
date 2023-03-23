package com.epam.esm.entities;

import com.epam.esm.util.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    @Column(name = "username")
    private String username;
    @JsonIgnore
    @Column(name = "user_password")
    private String password;
    @Column(name = "user_money")
    private BigDecimal money;
    @Column(name = "user_role")
    private UserRole role;
    @OneToMany(mappedBy = "owner")
    private List<Order> orders;
    @OneToMany
    private List<GiftCertificate> giftCertificates;
}