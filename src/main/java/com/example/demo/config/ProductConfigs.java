//package com.example.demo.config;
//
//import com.example.demo.entity.Product;
//import com.example.demo.repositories.ProductRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class ProductConfigs {
//    @Bean
//    CommandLineRunner commandLineRunner2(ProductRepository productRepository) {
//        return args -> {
//            Product cake = new Product(
//                    "cake",
//                    "desert",
//                    "100",
//                    "3"
//            );
//            Product donut = new Product(
//                    "donut",
//                    "desert",
//                    "200",
//                    "3"
//            );
//            productRepository.saveAll(
//                    List.of(cake, donut)
//            );
//        };
//    }
//}
