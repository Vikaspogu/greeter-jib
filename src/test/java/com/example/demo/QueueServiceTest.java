package com.example.demo;

import com.example.demo.jms.QueueService;
import com.example.demo.model.Order;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QueueServiceTest {

    private static final String QUEUE_NAME = "testQueue";

    @Autowired
    private QueueService queueService;

    @Rule
    public EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker() {
        @Override
        protected void configure() {
            try {
                this.getBrokerService().addConnector("tcp://localhost:61616");

            } catch (Exception e) {
                // noop test should fail
            }
        }
    };

    @Test
    public void testSend() throws Exception {
        queueService.sendMessage(QUEUE_NAME, new Order(UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, "test product", new Date()));
        Assert.assertEquals(1, queueService.pendingJobs(QUEUE_NAME));
    }

    @Test
    public void testReceive() throws Exception {
        queueService.receiveMessage(new Order(UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, "test product", new Date()));
        Assert.assertEquals(1, queueService.completedJobs());
    }
}
