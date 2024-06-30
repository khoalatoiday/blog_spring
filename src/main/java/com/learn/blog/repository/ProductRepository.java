package com.learn.blog.repository;

import com.learn.blog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Viết custom query theo mẫu jpa cung cấp sẵn
    @Query("SELECT p from Product p WHERE " +
        "p.name LIKE CONCAT('%', :query, '%')" +
            "OR p.description LIKE CONCAT('%', :query, '%')"
    )
    List<Product> searchProducts(String query);

    // viết custom query native (giống query sql)
    @Query(value = "SELECT * from products p WHERE " +
            "p.name LIKE CONCAT('%', :query, '%')" +
            "OR p.description LIKE CONCAT('%', :query, '%')"
    , nativeQuery = true)
    List<Product> searchProductsSql(String query);
}
