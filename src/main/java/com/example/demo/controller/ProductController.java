package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.exception.AddNewObjectException;
import com.example.demo.exception.DeleteObjectException;
import com.example.demo.exception.UpdateObjectException;
import com.example.demo.response.Response;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @GetMapping("/pricegreaterthan/{price}")
//    public List<Map<String, String>> getPriceGreaterThan(@PathVariable("price") Integer price) {
//        List<Product> rslt = productService.getPriceGreaterThan(price);
//        return Response.generateResponseProduct(rslt);
//    }
//
//    @GetMapping("/amountgreaterthan/{amount}")
//    public List<Map<String, String>> getAmountGreaterThan(@PathVariable("amount") Integer amount) {
//        List<Product> rslt = productService.getAmountGreaterThan(amount);
//        return Response.generateResponseProduct(rslt);
//    }

    @PostMapping
    public void addNewProduct(@RequestBody Product product) throws AddNewObjectException {
        productService.addNewProduct(product);
    }

    @DeleteMapping(path = "/{productID}")
    public void deleteProduct(@PathVariable("productID") Long productID) throws DeleteObjectException {
        productService.deleteProduct(productID);
    }

    @PutMapping(path = "/{productID}")
    public void updateProduct(@PathVariable("productID") Long productID
            , @RequestParam(required = false) String name
            , @RequestParam(required = false) String category
            , @RequestParam(required = false) Integer unitPrice
            , @RequestParam(required = false) Integer amount) throws UpdateObjectException {
        productService.updateProduct(productID, name, category, unitPrice, amount);
    }
}
