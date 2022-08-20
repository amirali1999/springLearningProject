package com.example.demo.service;

import com.example.demo.document.Log;
import com.example.demo.entity.Product;
import com.example.demo.exception.AddNewObjectException;
import com.example.demo.exception.DeleteObjectException;
import com.example.demo.exception.UpdateObjectException;
import com.example.demo.repositories.LogRepository;
import com.example.demo.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final LogRepository logRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, LogRepository logRepository) {
        this.productRepository = productRepository;
        this.logRepository = logRepository;
    }

    public List<Product> getProduct() {
        log.info("******************* get products *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"get product"));

        return productRepository.findAll();
    }

    public void addNewProduct(Product product) throws AddNewObjectException {
        log.info("******************* add product *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"add product"));

        if (productRepository.equals(product)) {
            throw new AddNewObjectException("the added product Exists!");
        }
        productRepository.save(product);

    }

    public void deleteProduct(Long productID) throws DeleteObjectException {
        log.info("******************* delete product *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"delete product"));

        if (!productRepository.existsById(productID)) {
            throw new DeleteObjectException("the requested user not found");
        }
        productRepository.deleteById(productID);
    }

//    @Transactional
    public void updateProduct(Long productID, String name, String category, Integer unitPrice, Integer amount) throws UpdateObjectException {
        log.info("******************* update product *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"update product"));

        Product product = productRepository.findById(productID).orElseThrow(() -> new IllegalArgumentException("can not add"));
        if (!productRepository.existsById(productID)) {
            throw new UpdateObjectException("user not found!");
        }
        if (name != null && name.length() > 0 && !Objects.equals(product.getName(), name)) {
            product.setName(name);
        }
        if (category != null && category.length() > 0 && !Objects.equals(product.getCategory(), category)) {
            product.setCategory(category);
        }
        if (unitPrice != null && unitPrice >= 0 && !Objects.equals(product.getUnitPrice(), unitPrice)) {
            product.setUnitPrice(unitPrice);
        }
        if (amount != null && amount >= 0 && !Objects.equals(product.getAmount(), amount)) {
            product.setAmount(amount);
        }
    }
}
