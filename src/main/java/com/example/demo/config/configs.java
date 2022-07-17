package com.example.demo.config;

import com.example.demo.entity.Invoice;
import com.example.demo.entity.Product;
import com.example.demo.entity.Users;
import com.example.demo.repositories.InvoiceRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class configs {
    @Bean
    CommandLineRunner commandLineRunner(
            UsersRepository usersRepository, ProductRepository productRepository, InvoiceRepository invoiceRepository) {
        return args -> {
            Users amirali = new Users(
                    "amirali",
                    "amirali99",
                    "admin",
                    "123456"
            );
            Users joe = new Users(
                    "mehdi",
                    "joe",
                    "user",
                    "123456"
            );
            Product cake = new Product(
                    "cake",
                    "desert",
                    100,
                    3
            );
            Product donut = new Product(
                    "donut",
                    "desert",
                    200,
                    6
            );
            List<Product> listOfProducts1 = new ArrayList<>();
            List<Product> listOfProducts2 = new ArrayList<>();
            listOfProducts1.add(donut);
            listOfProducts1.add(cake);
            listOfProducts2.add(donut);
            Invoice one = new Invoice(listOfProducts1, joe);
            Invoice two = new Invoice(listOfProducts2, amirali);
            invoiceRepository.saveAll(List.of(one, two));
        };
    }
}
