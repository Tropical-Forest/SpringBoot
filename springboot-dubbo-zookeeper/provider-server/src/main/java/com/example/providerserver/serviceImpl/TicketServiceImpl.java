package com.example.providerserver.serviceImpl;

import com.example.providerserver.service.TicketService;

import org.apache.dubbo.config.annotation.DubboService;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Service
@Component
public class TicketServiceImpl implements TicketService {
    @Override
    public String getTicket() {
        return "实现SpringBoot+Dubbo+Zookeeper";
    }
}
