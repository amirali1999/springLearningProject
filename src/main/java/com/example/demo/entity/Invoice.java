package com.example.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;
    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name="invoice_product",joinColumns=@JoinColumn(name = "invoice_id"),inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> product;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "users_id")
    private Users users;
    @Transient
    private int totalPrice;

    public Invoice(List<Product> product, Users users) {
        this.product = product;
        this.users = users;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice += totalPrice;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return id == invoice.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
