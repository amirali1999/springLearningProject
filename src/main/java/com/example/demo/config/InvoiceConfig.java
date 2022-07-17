//package com.example.demo.config;
//
//import com.example.demo.entity.Invoice;
//import com.example.demo.entity.Product;
//import com.example.demo.entity.Users;
//import com.example.demo.repositories.InvoiceRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class InvoiceConfig {
//    Product cake = new Product(
//            "cake",
//            "desert",
//            "100",
//            "3"
//    );
//    Product donut = new Product("donut", "desert", "200", "3");
//    Users amirali = new Users(
//            "amirali",
//            "amirali99",
//            "admin",
//            "123456"
//    );
//    Users joe = new Users(
//            "mehdi",
//            "joe",
//            "user",
//            "123456"
//    );
//    List<Product> listOfProducts1 = new ArrayList<>();
//    List<Product> listOfProducts2 = new ArrayList<>();
//
//    @Bean
//    CommandLineRunner commandLineRunner3(InvoiceRepository invoiceRepository) {
//        listOfProducts1.add(donut);
//        listOfProducts1.add(cake);
//        listOfProducts2.add(donut);
//
//        return args -> {
//            Invoice one = new Invoice(listOfProducts1, joe);
//            Invoice two = new Invoice(listOfProducts2, amirali);
//            invoiceRepository.saveAll(List.of(one,two));
//        };
//    }
//
//}
