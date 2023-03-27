package com.epam.esm.entities;

import com.epam.esm.util.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "users")
public class User{
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
    @JsonIgnore
    private UserRole role;
    @OneToMany(mappedBy = "owner")
    @JsonIgnoreProperties("owner")
    private List<Order> orders;
}