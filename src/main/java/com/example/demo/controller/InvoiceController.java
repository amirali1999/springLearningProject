package com.example.demo.controller;

import com.example.demo.entity.Invoice;
import com.example.demo.entity.InvoiceExample;
import com.example.demo.entity.Product;
import com.example.demo.entity.Users;
import com.example.demo.exception.AddNewObjectException;
import com.example.demo.exception.DeleteObjectException;
import com.example.demo.exception.UpdateObjectException;
import com.example.demo.response.Response;
import com.example.demo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public List<Map<String, String>> getInvoices() {
        List<Invoice> rslt = invoiceService.getInvoice();
        return Response.generateResponseInvoice(rslt);
    }

    @PostMapping(path = "/saveinvoice")
    public void addNewInvoice(@RequestBody InvoiceExample invoiceExample) throws AddNewObjectException {
        invoiceService.addNewInvoice(invoiceExample);
    }

    @DeleteMapping(path = "{invoiceID}")
    public void deleteInvoice(@PathVariable("invoiceID") Long invoiceID) throws DeleteObjectException {
        invoiceService.deleteInvoice(invoiceID);
    }

    @PutMapping(path = "{invoiceID}")
    public void updateInvoice(@PathVariable("invoiceID") Long invoiceID,@RequestBody InvoiceExample invoiceExample) throws UpdateObjectException {

        invoiceService.updateInvoice(invoiceID,invoiceExample);
    }

}
