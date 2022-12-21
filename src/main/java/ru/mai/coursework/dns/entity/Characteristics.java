package ru.mai.coursework.dns.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.mai.coursework.dns.entity.bridge.CategoryCh;
import ru.mai.coursework.dns.entity.bridge.ProductCh;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "characteristics")
@Getter
@Setter
public class Characteristics implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "characteristic_id")
    private int chId;

    @Column(name = "characteristic_name")
    private String chName;

    @OneToMany(mappedBy = "characteristic")
    List<ProductCh> productChs;

    @OneToMany(mappedBy = "characteristic")
    List<CategoryCh> categoriesCh;
}
