package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Entity @Table(name = "users",schema = "public",catalog = "springProject")
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;
    @Column(name = "name")
    private String name;
    @Column(name = "username")
    private String username;
    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "users",
               cascade = {CascadeType.DETACH,CascadeType.MERGE,
                          CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Invoice> invoice;

    public Users(String name, String username,Set<Role> roles, String password) {
        this.name = name;
        this.username = username;
        this.roles = roles;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", role='" + roles + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return id == users.id && Objects.equals(name, users.name) && Objects.equals(username, users.username) && Objects.equals(roles, users.roles) && Objects.equals(password, users.password) && Objects.equals(invoice, users.invoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, roles, password, invoice);
    }

    public void add(Invoice tempInvoice){
        if(invoice == null){
            invoice = new ArrayList<>();
        }
        invoice.add(tempInvoice);
        tempInvoice.setUsers(this);
    }
}
