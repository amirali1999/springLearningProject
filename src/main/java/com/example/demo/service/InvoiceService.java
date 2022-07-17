package com.example.demo.service;

import com.example.demo.entity.Invoice;
import com.example.demo.entity.Product;
import com.example.demo.entity.Users;
import com.example.demo.exception.AddNewObjectException;
import com.example.demo.exception.DeleteObjectException;
import com.example.demo.exception.UpdateObjectException;
import com.example.demo.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public void addNewInvoice(Invoice invoice) throws AddNewObjectException {
        if (invoiceRepository.equals(invoice)) {
            throw new AddNewObjectException("the added product Exists!");
        }
        invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Long invoiceID) throws DeleteObjectException {

        if (!invoiceRepository.existsById(invoiceID)) {
            throw new DeleteObjectException("the requested user not found");
        }
        invoiceRepository.deleteById(invoiceID);
    }

    public void updateInvoice(Long invoiceID, List<Product> product, Users users) throws UpdateObjectException {
        Invoice invoice = invoiceRepository.findById(invoiceID).orElseThrow(() -> new IllegalArgumentException("can not add"));
        if (!invoiceRepository.existsById(invoiceID)) {
            throw new UpdateObjectException("user not found!");
        }
        if (!product.isEmpty() && !Objects.equals(invoice.getProduct(), product)) {
            invoice.setProduct(product);
        }
        if (!users.equals(null) && !Objects.equals(invoice.getUsers(), users)) {
            invoice.setUsers(users);
        }
    }
}
