package ru.mai.coursework.dns.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name")
    private String productName;

    @OneToMany(mappedBy = "product")
    List<ProductCh> productChs;

    @OneToMany(mappedBy = "product")
    List<ProductCategory> productCategories;

    @OneToMany(mappedBy = "product")
    List<UserProducts> userProducts;
}
