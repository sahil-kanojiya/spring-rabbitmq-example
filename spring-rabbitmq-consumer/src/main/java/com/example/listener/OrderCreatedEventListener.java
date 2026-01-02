package com.example.listener;

import com.example.config.RabbitConfig;
import com.example.entity.ProcessedEvent;
import com.example.entity.constant.EventType;
import com.example.events.OrderCreatedEvent;
import com.example.repository.ProcessedEventRepository;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderCreatedEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrderCreatedEventListener.class);

    private final ProcessedEventRepository processedEventRepository;

    public OrderCreatedEventListener(ProcessedEventRepository processedEventRepository) {
        this.processedEventRepository = processedEventRepository;
    }

    @Transactional
    @RabbitListener(queues = RabbitConfig.ORDER_QUEUE)
    public void handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent, Channel channel, Message message) throws Exception {
        String eventId = orderCreatedEvent.eventId();

        boolean alreadyProcessed = processedEventRepository
                .findByEventIdAndEventType(eventId, EventType.ORDER_CREATED)
                .isPresent();

        if (!alreadyProcessed) {
            log.info("Processing: {}", orderCreatedEvent);
            processedEventRepository.save(new ProcessedEvent(eventId, EventType.ORDER_CREATED));
        }

        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
