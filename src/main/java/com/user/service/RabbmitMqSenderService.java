package com.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbmitMqSenderService {

	private static final Logger logger = LoggerFactory.getLogger(RabbmitMqSenderService.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private Queue queue;

	public void send() {
		String message = "Hello World!";
		this.rabbitTemplate.convertAndSend(queue.getName(), message);
		logger.info(" [x] Sent '" + message + "'");
	}

}
