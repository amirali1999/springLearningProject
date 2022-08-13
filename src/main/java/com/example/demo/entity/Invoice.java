package com.example.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.serializer.Serializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Getter @Setter @NoArgsConstructor @Entity @Table(name = "invoice")
public class Invoice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id = 0;
    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name="invoice_product",joinColumns=@JoinColumn(name = "invoice_id"),inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> product;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "users_id",insertable = false,updatable = false)
    private Users users;
    @Column(name = "users_id")
    private long users_id;
    private int totalPrice;
    @Column(name = "delivery_status")
    @Enumerated(EnumType.STRING)
    private EOrder deliveryStatus;

    public Invoice(List<Product> product, Users users) {
        this.product = product;
        this.users = users;
        this.users_id = users.getId();
        setTotalPrice();
    }

    public void setTotalPrice() {
        for(Product product:getProduct()){
            this.totalPrice += product.getUnitPrice();
        }
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", product=" + product +
                ", users=" + users +
                ", totalPrice=" + totalPrice +
                ", deliveryStatus="+deliveryStatus+
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
