package com.example.demo.service;

import com.example.demo.entity.Invoice;
import com.example.demo.entity.InvoiceExample;
import com.example.demo.entity.Product;
import com.example.demo.entity.Users;
import com.example.demo.exception.AddNewObjectException;
import com.example.demo.exception.DeleteObjectException;
import com.example.demo.exception.UpdateObjectException;
import com.example.demo.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> getInvoice() {
        return invoiceRepository.findAll();
    }

    public void addNewInvoice(InvoiceExample invoiceExample) throws AddNewObjectException {
        Users users = invoiceRepository.findUser(invoiceExample.users_id);
        List<Product> products = new ArrayList<>();
        IntStream.range(0,invoiceExample.product_id.length)
                .forEach(i->{products.add(invoiceRepository.findProduct(invoiceExample.product_id[i]));});
        Invoice invoice = new Invoice(products,users);
        invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Long invoiceID) throws DeleteObjectException {

        if (!invoiceRepository.existsById(invoiceID)) {
            throw new DeleteObjectException("the requested user not found");
        }
        invoiceRepository.deleteById(invoiceID);
    }

    public void updateInvoice(Long invoiceID,InvoiceExample invoiceExample) throws UpdateObjectException {
        Invoice invoice = invoiceRepository.findById(invoiceID).orElseThrow(() -> new IllegalArgumentException("can not add"));
        if (!invoiceRepository.existsById(invoiceID)) {
            throw new UpdateObjectException("user not found!");
        }
        List<Product> newProductList = new ArrayList<>();
        for (long newProductId:invoiceExample.product_id){
            Product newProduct = invoiceRepository.findProduct(newProductId);
            newProductList.add(newProduct);
        }
        if(!invoice.getProduct().equals(newProductList)){
            invoice.setProduct(newProductList);
        }
        Users newUser = invoiceRepository.findUser(invoiceExample.users_id);
        if (!invoice.getUsers().equals(newUser)) {
            invoice.setUsers(newUser);
        }
        invoiceRepository.save(invoice);
    }
}
