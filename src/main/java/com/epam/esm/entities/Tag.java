package com.epam.esm.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;
    @Column(name = "tag_name", unique = true)
    private String name;
    @ManyToMany(mappedBy = "tags")
    @JsonIgnoreProperties("tags")
    private List<GiftCertificate> certificates;

    public Tag(String name) {
        this.name = name;
    }
}
