package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "order")
public class Order implements Serializable {

    @Id
    private String orderId;
    private long quantity;
    private String productName;

    public Order() {
    }

    public Order(String orderId, long quantity, String productName) {
        this.orderId = orderId;
        this.quantity = quantity;
        this.productName = productName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "Id=" + orderId +
                ", quantity=" + quantity +
                ", productName='" + productName + '\'' +
                '}';
    }
}
