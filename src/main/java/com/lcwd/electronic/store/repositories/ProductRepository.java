package com.lcwd.electronic.store.repositories;

import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    //custom finder methods
    Page<Product> findByTitleContaining(String keyword, Pageable pageable);

    Page<Product> findByActiveProductTrue(Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);

}
