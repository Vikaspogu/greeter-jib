package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "order")
public class Order implements Serializable {

    @Id
    private String id;
    private String orderNumber;
    private long quantity;
    private String productName;
    private Date timestamp;

    public Order() {
    }

    public Order(String id, String orderNumber, long quantity, String productName, Date timestamp) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.quantity = quantity;
        this.productName = productName;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Order{" +
                "Id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", quantity='" + quantity + '\'' +
                ", productName='" + productName + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
