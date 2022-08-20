package com.example.demo.repositories;

import com.example.demo.entity.EOrder;
import com.example.demo.entity.Invoice;
import com.example.demo.entity.Product;
import com.example.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
//    @Query(value = "SELECT s FROM Users s WHERE s.id=:#{#users_id}")
//    Users findUser(@Param("users_id") long usersId);

//    @Query(value = "SELECT s FROM Product s WHERE s.id=:#{#product_id}")
//    Product findProduct(@Param("product_id") long productID);

    @Modifying
    @Query(value = "update Invoice u set u.deliveryStatus = :status where u.id = :id")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void updateStatus(@Param(value = "id") long id, @Param(value = "status") EOrder status);

}
