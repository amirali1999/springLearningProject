package com.example.demo.controller;

import com.example.demo.entity.Invoice;
import com.example.demo.payload.request.InvoiceRequest;
import com.example.demo.exception.type.AddNewObjectException;
import com.example.demo.exception.type.DeleteObjectException;
import com.example.demo.exception.type.UpdateObjectException;
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
    public void addNewInvoice(@RequestBody InvoiceRequest invoiceRequest) throws AddNewObjectException {
        invoiceService.addNewInvoice(invoiceRequest);
    }

    @DeleteMapping(path = "{invoiceID}")
    public String deleteInvoice(@PathVariable("invoiceID") Long invoiceID) throws DeleteObjectException {
        return invoiceService.deleteInvoice(invoiceID);
    }

    @PutMapping(path = "{invoiceID}")
    public void updateInvoice(@PathVariable("invoiceID") Long invoiceID,@RequestBody InvoiceRequest invoiceRequest) throws UpdateObjectException {
        invoiceService.updateInvoice(invoiceID, invoiceRequest);
    }

}
