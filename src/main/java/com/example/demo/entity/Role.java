package com.example.demo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ERole name;
    public Role() {
    }
    public Role(ERole name) {
        this.name = name;
    }
    public long getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public ERole getName() {
        return name;
    }
    public void setName(ERole name) {
        this.name = name;
    }
}
