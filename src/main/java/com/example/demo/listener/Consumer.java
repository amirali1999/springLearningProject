package com.example.demo.listener;

import com.example.demo.entity.EOrder;
import com.example.demo.entity.Invoice;
import com.example.demo.repositories.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public Consumer(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @JmsListener(destination = "amirali.queue")
    public void consume(Invoice invoice) {
        try {
            Thread.sleep(20000);
            invoice.setDeliveryStatus(EOrder.DELIVERED);
            invoiceRepository.updateStatus(invoice.getId(), EOrder.DELIVERED);
            log.info("received the delivery: " + invoice);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
