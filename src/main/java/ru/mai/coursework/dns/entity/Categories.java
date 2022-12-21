package ru.mai.coursework.dns.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.mai.coursework.dns.entity.bridge.CategoryCh;
import ru.mai.coursework.dns.entity.bridge.ProductCategory;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Categories implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "upper_category_id")
    private int upCategoryId;

    @Column(name = "name_category")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    List<ProductCategory> productCategories;

    @OneToMany(mappedBy = "category")
    List<CategoryCh> categoriesCh;
}
