package ru.mai.coursework.dns.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "variants_of_cat_characteristics")
@Getter
@Setter
public class VariantsCategoryCh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "var_id")
    private int variantId;

    @Column(name = "var_value")
    private String variantValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_ch_id")
    CategoryCh categoryCh;
}
