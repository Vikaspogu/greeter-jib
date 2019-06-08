package com.example.demo.model;

public class Order {

    @org.springframework.data.annotation.Id
    private String id;
    private long quantity;
    private String productName;

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
                "Id=" + id +
                ", quantity=" + quantity +
                ", productName='" + productName + '\'' +
                '}';
    }
}
