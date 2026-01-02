package com.example.events;

public record OrderCreatedEvent(String eventId, Long orderId, double amount) {
}
