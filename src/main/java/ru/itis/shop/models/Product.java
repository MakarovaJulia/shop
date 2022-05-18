package ru.itis.shop.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "amount")
    private double amount;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;
}
