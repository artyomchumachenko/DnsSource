package ru.mai.coursework.dns.entity.bridge;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.mai.coursework.dns.entity.Categories;
import ru.mai.coursework.dns.entity.Product;

@Entity
@Table(name = "product_category")
@Getter
@Setter
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_category_id")
    private int productCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Categories category;
}
