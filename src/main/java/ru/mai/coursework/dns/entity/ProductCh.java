package ru.mai.coursework.dns.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "product_characteristic")
@Getter
@Setter
public class ProductCh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_characteristic_id")
    private int productChId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

//    @OneToMany(mappedBy = "characteristic")
//    private List<Characteristics> characteristics;

    @Column(name = "characteristic_value")
    private String chValue;
}
