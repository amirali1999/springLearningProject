package com.example.demo.listener;

import com.example.demo.entity.EOrder;
import com.example.demo.entity.Invoice;
import com.example.demo.config.quartz.info.TimerInfo;
import com.example.demo.config.quartz.job.QuartzJob;
import com.example.demo.repositories.InvoiceRepository;
import com.example.demo.config.quartz.service.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    private final InvoiceRepository invoiceRepository;
    private final SchedulerService schedulerService;

    @Autowired
    public Consumer(InvoiceRepository invoiceRepository, SchedulerService schedulerService) {
        this.invoiceRepository = invoiceRepository;
        this.schedulerService = schedulerService;
    }

    @JmsListener(destination = "amirali.queue")
    public void consume(Invoice invoice) {
        final TimerInfo info = new TimerInfo();
        invoice.setDeliveryStatus(EOrder.DELIVERED);
        invoiceRepository.updateStatus(invoice.getId(), EOrder.DELIVERED);
        info.setTotalFireCount(1);
        info.setRepeatIntervslMs(0);
        info.setInitialOffsetms(10000);
        info.setCallbackData(invoice.toString());
        schedulerService.schedule(QuartzJob.class,info);
    }
}
