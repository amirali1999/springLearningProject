package com.example.demo.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private String category;
    private int unitPrice;
    private int amount;
}
