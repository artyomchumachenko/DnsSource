package ru.mai.coursework.dns.entity.bridge;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "characteristic_values")
@Getter
@Setter
public class ChValues {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "value_id")
    private int valueId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cat_ch_id")
    CategoryCh categoryCh;

    @Column(name = "value")
    private String value;
}
