package com.example.demo.service;

import com.example.demo.document.Log;
import com.example.demo.entity.*;
import com.example.demo.exception.AddNewObjectException;
import com.example.demo.exception.DeleteObjectException;
import com.example.demo.exception.UpdateObjectException;
import com.example.demo.repositories.InvoiceRepository;
import com.example.demo.repositories.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Slf4j
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final LogRepository logRepository;
    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    Queue queue;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, LogRepository logRepository) {
        this.invoiceRepository = invoiceRepository;
        this.logRepository = logRepository;
    }

    public List<Invoice> getInvoice() {
        log.info("******************* get invoice *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"get invoice"));

        return invoiceRepository.findAll();
    }

    public void addNewInvoice(InvoiceExample invoiceExample) throws AddNewObjectException {
        log.info("******************* add invoice *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"add invoice"));

        Users users = invoiceRepository.findUser(invoiceExample.users_id);
        List<Product> products = new ArrayList<>();
        IntStream.range(0, invoiceExample.product_id.length)
                .forEach(i -> {
                    products.add(invoiceRepository.findProduct(invoiceExample.product_id[i]));
                });
        Invoice invoice = new Invoice(products, users);
        invoice.setDeliveryStatus(EOrder.DELIVERING);
        invoiceRepository.save(invoice);

        //use activemq for send invoice to consumer
        log.info("delivery send to consumer - delivery details: " + invoice);
        jmsTemplate.convertAndSend(queue, invoice);
    }

    public void deleteInvoice(Long invoiceID) throws DeleteObjectException {
        log.info("******************* delete invoice *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"delete invoice"));

        if (!invoiceRepository.existsById(invoiceID)) {
            throw new DeleteObjectException("the requested user not found");
        }
        invoiceRepository.deleteById(invoiceID);
    }

    public void updateInvoice(Long invoiceID, InvoiceExample invoiceExample) throws UpdateObjectException {
        log.info("******************* update invoice *******************");
        logRepository.save(new Log(LocalDateTime.now().toString(),"update invoice"));

        Invoice invoice = invoiceRepository.findById(invoiceID).orElseThrow(() -> new IllegalArgumentException("can not add"));
        if (!invoiceRepository.existsById(invoiceID)) {
            throw new UpdateObjectException("user not found!");
        }
        List<Product> newProductList = new ArrayList<>();
        for (long newProductId : invoiceExample.product_id) {
            Product newProduct = invoiceRepository.findProduct(newProductId);
            newProductList.add(newProduct);
        }
        if (!invoice.getProduct().equals(newProductList)) {
            invoice.setProduct(newProductList);
        }
        Users newUser = invoiceRepository.findUser(invoiceExample.users_id);
        if (!invoice.getUsers().equals(newUser)) {
            invoice.setUsers(newUser);
        }
        invoice.setDeliveryStatus(EOrder.DELIVERED);
        invoiceRepository.save(invoice);
    }
}
