package com.lcwd.electronic.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "products") // imp. otherwise getting error.
@Builder
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "id")
    private String categoryId;
    @Column(name = "category_title", length = 100, nullable = false)
    private String title;
    @Column(name = "category_description", length = 500)
    private String description;
    private String coverImage;

    // mapping
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    List<Product> products;
}
