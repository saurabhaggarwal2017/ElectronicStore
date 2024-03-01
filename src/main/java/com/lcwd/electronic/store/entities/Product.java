package com.lcwd.electronic.store.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    private String productId;
    @Column(length = 80)
    private String title;
    @Column(length = 10000)
    private String description;
    @Column(length = 30)
    private String brand;
    private int price;
    private int quantity;
    private Date productAddedDate;
    private boolean activeProduct;
    private boolean stock;
    private String productImage;

}
