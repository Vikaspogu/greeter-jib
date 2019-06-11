package com.example.demo;

import com.example.demo.model.Order;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    public void submitOrderTest() throws Exception {
        mockMvc.perform(post("/submit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .sessionAttr("order", new Order(UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                        1, "test product", new Date())))
                .andExpect(status().isOk())
                .andExpect(view().name("success"))
                .andExpect(model().attribute("order", hasProperty("quantity", is(0L))))
                .andExpect(model().attribute("order", hasProperty("productName", isEmptyOrNullString())));
    }

    @Test
    public void homeOrderTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("isConnected"))
                .andExpect(model().attributeExists("queueName"));
    }
}
