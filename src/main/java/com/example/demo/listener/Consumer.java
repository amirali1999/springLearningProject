package com.example.demo.listener;

import com.example.demo.entity.EOrder;
import com.example.demo.entity.Invoice;
import com.example.demo.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public Consumer(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @JmsListener(destination = "amirali.queue")
    public void consume(Invoice invoice) {
        try{
            Thread.sleep(20000);
            invoice.setDeliveryStatus(EOrder.DELIVERED);
            invoiceRepository.updateStatus(invoice.getId(), EOrder.DELIVERED);
            System.out.println("received the delivery: " + invoice);
        }catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
