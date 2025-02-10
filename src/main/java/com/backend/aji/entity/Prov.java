package com.backend.aji.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "provs")
public class Prov {
    @Id
    private Long id;
    private String name;

//    @OneToMany(mappedBy = "prov", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<City> cities;

}
