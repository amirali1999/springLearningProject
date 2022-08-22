package com.example.demo.service;

import com.example.demo.document.Log;
import com.example.demo.entity.*;
import com.example.demo.exception.type.AddNewObjectException;
import com.example.demo.exception.type.DeleteObjectException;
import com.example.demo.exception.type.UpdateObjectException;
import com.example.demo.payload.request.InvoiceRequest;
import com.example.demo.repositories.InvoiceRepository;
import com.example.demo.repositories.LogRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@Slf4j
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;
    private final LogRepository logRepository;
    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    Queue queue;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, ProductRepository productRepository, UsersRepository usersRepository, LogRepository logRepository) {
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
        this.usersRepository = usersRepository;
        this.logRepository = logRepository;
    }

    public List<Invoice> getInvoice() {
        log.info("******************* get invoice *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"get invoice"));

        return invoiceRepository.findAll();
    }

    public void addNewInvoice(InvoiceRequest invoiceRequest) throws AddNewObjectException {
        log.info("******************* add invoice *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"add invoice"));

        Optional<Users> users = usersRepository.findById(invoiceRequest.users_id).or(()->{
            log.error("the added Invoice exists !");
            logRepository.save(new Log(LocalDateTime.now().toString(),"Error:the added Invoice exists !"));
            throw new AddNewObjectException("the added Invoice exists !");
        });
        List<Product> products = new ArrayList<>();
        IntStream.range(0, invoiceRequest.product_id.length)
                .forEach(i -> {
                    products.add(productRepository.findProduct(invoiceRequest.product_id[i]));
                });
        Invoice invoice = new Invoice(products, users.get());
        invoice.setDeliveryStatus(EOrder.DELIVERING);
        invoiceRepository.save(invoice);

        //use activemq for send invoice to consumer
        log.info("delivery send to consumer - delivery details: " + invoice);
        jmsTemplate.convertAndSend(queue, invoice);
    }

    public String deleteInvoice(Long invoiceID) throws DeleteObjectException {

        if (invoiceRepository.existsById(invoiceID)) {
            log.info("******************* delete invoice *******************");
            logRepository.save(new Log(LocalDateTime.now().toString(),"delete invoice "+invoiceID));
            invoiceRepository.deleteById(invoiceID);
            return "invoice "+invoiceID+" delete successfully";
        }
        else {
            log.error("invoice "+invoiceID+" not found");
            logRepository.save(new Log(LocalDateTime.now().toString(),"invoice "+invoiceID+" not found"));
            throw new DeleteObjectException("invoice "+invoiceID+" not found");
        }
    }

    public void updateInvoice(Long invoiceID, InvoiceRequest invoiceRequest) throws UpdateObjectException {
        log.info("******************* update invoice *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"update invoice"));

        Optional<Invoice> invoice = invoiceRepository.findById(invoiceID).or(() -> {
            log.info("invoice not found");
            logRepository.save(new Log(LocalDateTime.now().toString(),"invoice not found"));
            throw new UpdateObjectException("invoice not found");
        });
        List<Product> newProductList = new ArrayList<>();
        for (long newProductId : invoiceRequest.product_id) {
            Product newProduct = productRepository.findProduct(newProductId);
            newProductList.add(newProduct);
        }
        if (!invoice.get().getProduct().equals(newProductList)) {
            invoice.get().setProduct(newProductList);
        }
        Users newUser = usersRepository.findById(invoiceRequest.users_id).get();
        if (!invoice.get().getUsers().equals(newUser)) {
            invoice.get().setUsers(newUser);
        }
        invoice.get().setDeliveryStatus(EOrder.DELIVERED);
        invoiceRepository.save(invoice.get());
    }
}
