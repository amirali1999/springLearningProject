package com.example.demo.repositories;

import com.example.demo.entity.Product;
import com.example.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT s FROM Product s WHERE s.unitPrice>=:#{#price}")
    List<Product> priceBiggerThan(@Param("price") Integer price);

    @Query(value = "SELECT s FROM Product s WHERE s.amount>=:#{#amount}")
    List<Product> amountBiggerThan(@Param("amount") Integer amount);
}
