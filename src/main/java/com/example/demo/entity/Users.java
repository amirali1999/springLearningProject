package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;
    private String name;
    private String username;
    private String role;
    private String password;
    @OneToMany(mappedBy = "users",
               cascade = {CascadeType.DETACH,CascadeType.MERGE,
                          CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Invoice> invoice;

    public Users(String name, String username, String role, String password) {
        this.name = name;
        this.username = username;
        this.role = role;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return id == users.id && Objects.equals(name, users.name) && Objects.equals(username, users.username) && Objects.equals(role, users.role) && Objects.equals(password, users.password) && Objects.equals(invoice, users.invoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, role, password, invoice);
    }

    public void add(Invoice tempInvoice){
        if(invoice == null){
            invoice = new ArrayList<>();
        }
        invoice.add(tempInvoice);
        tempInvoice.setUsers(this);
    }
}
