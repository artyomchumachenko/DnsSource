package ru.mai.coursework.dns.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_characteristic")
@Getter
@Setter
public class ProductCh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_characteristic_id")
    private int productChId;

    @Column(name = "characteristic_value")
    private String chValue;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne
    @JoinColumn(name = "characteristic_id")
    Characteristics characteristic;
}
