package com.example.demo.config;

import com.example.demo.entity.Invoice;
import com.example.demo.entity.Product;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.repositories.InvoiceRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.demo.entity.ERole.ROLE_ADMIN;
import static com.example.demo.entity.ERole.ROLE_USER;

//import static com.example.demo.entity.ERole.ROLE_ADMIN;
//import static com.example.demo.entity.ERole.ROLE_USER;

@Configuration
public class configs {
    @Bean
    CommandLineRunner commandLineRunner(
            UsersRepository usersRepository, ProductRepository productRepository, InvoiceRepository invoiceRepository) {
        return args -> {
            Role aarole = new Role();
            Role jrole = new Role();
            aarole.setName(ROLE_USER);
            jrole.setName(ROLE_USER);
            jrole.setName(ROLE_ADMIN);
            Set<Role> amiraliRoles = new HashSet<>();
            Set<Role> joeRoles = new HashSet<>();
            amiraliRoles.add(aarole);
            joeRoles.add(jrole);
            Users amirali = new Users(
                    "amirali",
                    "amirali99",
                    amiraliRoles,
                    "123456"
            );
            Users joe = new Users(
                    "mehdi",
                    "joe",
                    joeRoles,
                    "$2y$10$m2cZEvlMTMOai9b7j0eGH.mDUcBxbADvDqoYbRma.tvBZI6MUROXO"
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
