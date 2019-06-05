package com.example.demo.jms;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Collections;

@Component
public class QueueService implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueService.class);

    @Autowired
    private JmsTemplate jmsTemplate;
    private int counter = 0;

    public int completedJobs() {
        return counter;
    }

    public int pendingJobs(String queue) {
        return jmsTemplate.browse(queue, (s, qb) -> Collections.list(qb.getEnumeration()).size());
    }

    public void sendMessage(String destination, String message) {
        LOGGER.info("sending message='{}' to destination='{}'", message, destination);
        jmsTemplate.convertAndSend(destination, message);
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

    @Override
    public void onMessage(Message message) {
        if (message instanceof ActiveMQTextMessage) {
            ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
            try {
                LOGGER.info("Processing task " + textMessage.getText());
                Thread.sleep(5000);
                LOGGER.info("Completed task " + textMessage.getText());
            } catch (InterruptedException | JMSException e) {
                e.printStackTrace();
            }
            counter++;
        } else {
            LOGGER.error("Message is not a text message " + message.toString());
        }
    }
}
