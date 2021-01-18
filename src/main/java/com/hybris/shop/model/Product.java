package com.hybris.shop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "products")
@EqualsAndHashCode(of = {"id", "name", "price", "status", "createdAt"})
@ToString(of = {"id", "name", "price", "status", "createdAt"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    public enum ProductStatus{
        OUT_OF_STOCK, IN_STOCK, RUNNING_LOW
    }
}
