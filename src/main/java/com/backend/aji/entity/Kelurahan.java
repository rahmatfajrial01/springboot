package com.backend.aji.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "kelurahans")
public class Kelurahan  {
    @Id
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;
}
