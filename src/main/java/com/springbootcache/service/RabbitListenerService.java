package com.springbootcache.service;

import org.springframework.amqp.core.Message;

public interface RabbitListenerService {
    void listenRabbit(Message message);
}
