package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.exception.type.AddNewObjectException;
import com.example.demo.exception.type.DeleteObjectException;
import com.example.demo.exception.type.UpdateObjectException;
import com.example.demo.payload.request.ProductRequest;
import com.example.demo.response.Response;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Map<String, String>> getProducts() {
        List<Product> rslt = productService.getProduct();
        return Response.generateResponseProduct(rslt);
    }

    @PostMapping
    public ResponseEntity<?> addNewProduct(@RequestBody Product product) throws AddNewObjectException {
        return  productService.addNewProduct(product);
    }

    @DeleteMapping(path = "/{productname}/{category}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productname") String productName,@PathVariable("category") String category) throws DeleteObjectException {
        return productService.deleteProduct(productName,category);
    }

    @PutMapping(path = "/{productName}/{category}")
    public ResponseEntity<?> updateProduct(@PathVariable("productName") String productName,
                                           @PathVariable("category") String category,
                                           @RequestBody ProductRequest productRequest)
            throws UpdateObjectException {
        return productService.updateProduct(productName, category,productRequest);
    }
}
