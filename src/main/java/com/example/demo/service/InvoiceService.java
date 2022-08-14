package com.example.demo.service;

import com.example.demo.config.QuartzConfig;
import com.example.demo.entity.*;
import com.example.demo.exception.AddNewObjectException;
import com.example.demo.exception.DeleteObjectException;
import com.example.demo.exception.UpdateObjectException;
import com.example.demo.repositories.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Slf4j
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    Queue queue;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> getInvoice() {
        log.info("******************* get invoice *******************");
        return invoiceRepository.findAll();
    }

    public void addNewInvoice(InvoiceExample invoiceExample) throws AddNewObjectException {
        log.info("******************* add invoice *******************");
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
        if (!invoiceRepository.existsById(invoiceID)) {
            throw new DeleteObjectException("the requested user not found");
        }
        invoiceRepository.deleteById(invoiceID);
    }

    public void updateInvoice(Long invoiceID, InvoiceExample invoiceExample) throws UpdateObjectException {
        log.info("******************* update invoice *******************");
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
