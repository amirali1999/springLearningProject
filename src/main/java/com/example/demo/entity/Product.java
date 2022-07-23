package com.example.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor @Entity @Table
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;
    private String name;
    private String category;
    private Integer unitPrice;
    private Integer amount;
    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name="invoice_product",joinColumns=@JoinColumn(name = "product_id"),inverseJoinColumns = @JoinColumn(name = "invoice_id"))
    private List<Invoice> invoice;

    public Product(String name, String category, Integer unitPrice, Integer amount) {
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Objects.equals(name, product.name) && Objects.equals(category, product.category) && Objects.equals(unitPrice, product.unitPrice) && Objects.equals(amount, product.amount) && Objects.equals(invoice, product.invoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, unitPrice, amount, invoice);
    }
}
