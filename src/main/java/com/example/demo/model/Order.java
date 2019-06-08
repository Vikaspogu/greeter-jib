package com.example.demo.model;

import java.io.Serializable;

public class Order implements Serializable {

    private String orderId;
    private long quantity;
    private String productName;

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
