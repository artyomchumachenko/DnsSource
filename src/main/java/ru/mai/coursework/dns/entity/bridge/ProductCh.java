package ru.mai.coursework.dns.entity.bridge;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.mai.coursework.dns.entity.Characteristics;
import ru.mai.coursework.dns.entity.Product;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "characteristic_id")
    Characteristics characteristic;
}
