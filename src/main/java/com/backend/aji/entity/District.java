package com.backend.aji.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "districts")
public class District {
    @Id
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

//    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Kelurahan> kelurahans = new ArrayList<>();
}
