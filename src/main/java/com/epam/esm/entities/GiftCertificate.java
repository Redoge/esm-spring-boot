package com.epam.esm.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gift_certificate_id")
    private Long id;
    @Column(name = "gift_certificate_name", unique = true)
    private String name;
    @Column(name = "gift_certificate_description")
    private String description;
    @Column(name = "gift_certificate_price")
    private BigDecimal price;
    @Column(name = "gift_certificate_duration")
    private int duration;
    @Column(name = "gift_certificate_create_date")
    private LocalDateTime createDate;
    @Column(name = "gift_certificate_id_last_update_date")
    private LocalDateTime lastUpdateDate;
    @ManyToMany
    @JoinTable(name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonIgnoreProperties("certificates")
    private List<Tag> tags;

    public GiftCertificate(Long id, String name, String description, BigDecimal price, int duration, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }
}
