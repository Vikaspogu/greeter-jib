package com.example.demo.jms;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.util.Collections;

@Component
public class QueueService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueService.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private OrderRepository orderRepository;

    private int counter = 0;

    public int completedJobs() {
        return counter;
    }

    public int pendingJobs(String queue) {
        return jmsTemplate.browse(queue, (s, qb) -> Collections.list(qb.getEnumeration()).size());
    }

    public void sendMessage(String destination, Order order) {
        LOGGER.info("sending message='{}' to destination='{}'", order.getProductName(), destination);
        jmsTemplate.convertAndSend(destination, order);
    }

    public boolean isUp() {
        ConnectionFactory connection = jmsTemplate.getConnectionFactory();
        if (connection != null) {
            try {
                connection.createConnection().close();
                return true;
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @JmsListener(destination = "mainQueue")
    public void receiveMessage(Order order) {
        try {
            LOGGER.info("Processing task " + order.getProductName());
            orderRepository.save(order);
            Thread.sleep(5000);
            LOGGER.info("Completed task " + order.getProductName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        counter++;
    }
}
