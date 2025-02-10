package com.backend.aji.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "cities")
public class City {
    @Id
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "prov_id")
    private Prov prov;

//    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<District> districts = new ArrayList<>();

}
