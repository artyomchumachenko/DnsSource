package ru.mai.coursework.dns.entity.bridge;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.mai.coursework.dns.entity.Categories;
import ru.mai.coursework.dns.entity.Characteristics;

import java.util.List;

@Entity
@Table(name = "category_characteristic")
@Getter
@Setter
public class CategoryCh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_ch_id")
    private int categoryChId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Categories category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "characteristic_id")
    Characteristics characteristic;

    @OneToMany(mappedBy = "categoryCh")
    List<ChValues> chValues;
}
