package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String product;
    private double amount;

    public Order() {}

    public Order(String product, double amount) {
        this.product = product;
        this.amount = amount;
    }

    public Long getId() { return id; }
    public String getProduct() { return product; }
    public double getAmount() { return amount; }
    public void setProduct(String product) { this.product = product; }
    public void setAmount(double amount) { this.amount = amount; }
}
