package com.example.demo.service;

import com.example.demo.document.Log;
import com.example.demo.entity.Product;
import com.example.demo.exception.type.AddNewObjectException;
import com.example.demo.exception.type.DeleteObjectException;
import com.example.demo.exception.type.UpdateObjectException;
import com.example.demo.payload.request.ProductRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repositories.LogRepository;
import com.example.demo.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public ResponseEntity<?> addNewProduct(@Valid @RequestBody Product product) throws AddNewObjectException {
        log.info("******************* add product *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"add product"));

        if (productRepository.existsByProductNameAndCategory(product.getName(),product.getCategory()).isPresent()) {
            log.error("the added product exists !");
            logRepository.save(new Log(LocalDateTime.now().toString(),"Error:the added product exists !"));
            throw new AddNewObjectException("the added product exists !");
        }
        productRepository.save(product);
        return ResponseEntity.ok(new MessageResponse("product add successfully !"));
    }
    @Transactional
    public ResponseEntity<?> deleteProduct(String productName,String category) throws DeleteObjectException {
        if (productRepository.existsByProductNameAndCategory(productName,category).isPresent()){
            log.info("******************* delete product *******************");
            logRepository.save(new Log(LocalDateTime.now().toString(),"delete product "+productName));
            productRepository.deleteProduct(productName,category);
            return ResponseEntity.ok(new MessageResponse("product delete successfully!"));
        }
        else{
            log.error("product "+productName+" in category "+category+" not found");
            logRepository.save(new Log(LocalDateTime.now().toString(),"product "+productName+" in category "+category+" not found"));
            throw new DeleteObjectException("product "+productName+" in category "+category+" not found");
        }
    }

//    @Transactional
    public ResponseEntity<?> updateProduct(String productName, String category, ProductRequest productRequest) throws UpdateObjectException {
        log.info("******************* update product *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"update product"));
        Optional<Product> product = productRepository.existsByProductNameAndCategory(productName,category)
                .or(() -> {
                    log.info("product not found");
                    logRepository.save(new Log(LocalDateTime.now().toString(),"product not found"));
                    throw new UpdateObjectException("product not found");
                });
        if (!productRequest.getName().isEmpty() &&
                !product.get().getName().equals(productRequest.getName()) &&
                !productRepository.existsByProductNameAndCategory(productRequest.getName(),category).isPresent()) {
            product.get().setName(productRequest.getName());
        }
        if (!productRequest.getCategory().isEmpty() &&
                !Objects.equals(product.get().getCategory(), productRequest.getCategory()) &&
                !productRepository.existsByProductNameAndCategory(productName,productRequest.getCategory()).isPresent()) {
            product.get().setCategory(productRequest.getCategory());
        }
        if (productRequest.getUnitPrice() >= 0 && !Objects.equals(product.get().getUnitPrice(), productRequest.getUnitPrice())) {
            product.get().setUnitPrice(productRequest.getUnitPrice());
        }
        if (productRequest.getAmount() >= 0 && !Objects.equals(product.get().getAmount(), productRequest.getAmount())) {
            product.get().setAmount(productRequest.getAmount());
        }
        return ResponseEntity.ok(new MessageResponse("product update successfully!"));
    }
}
