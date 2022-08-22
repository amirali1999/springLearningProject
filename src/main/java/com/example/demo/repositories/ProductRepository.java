package com.example.demo.repositories;

import com.example.demo.entity.Product;
import com.example.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT s FROM Product s WHERE s.id=:#{#product_id}")
    Product findProduct(@Param("product_id") long productID);
    @Query(value = "SELECT s FROM Product s WHERE s.name=:#{#name} and s.category=:#{#category}")
    Optional<Product> existsByProductNameAndCategory(@Param("name") String name,@Param("category") String category);
    @Modifying
    @Query(value = "DELETE FROM Product s WHERE s.name=:#{#name} and s.category=:#{#category}")
    void deleteProduct(@Param("name") String name,@Param("category") String category);
}
