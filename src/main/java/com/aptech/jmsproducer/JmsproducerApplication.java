package com.aptech.jmsproducer;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

@SpringBootApplication
public class JmsproducerApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(JmsproducerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JmsproducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		ConnectionFactory connectionFactory = new JmsConnectionFactory("amqp://huutri-pc:56722");
		Connection connection = connectionFactory.createConnection("admin", "admin");
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//Queue
		//Destination destination = session.createQueue("jms-queue");
		//Topic
		Destination destination = session.createTopic("jms-Topic");
		MessageProducer messageProducer = session.createProducer(destination);
		

		for (int i = 1001; i <= 2000; i++) {

			TextMessage msg = session.createTextMessage("Hello world " + i);
			messageProducer.send(msg);
			logger.info("Sent: {}", msg.getText());

		}
		TextMessage msg = session.createTextMessage("closed");
			messageProducer.send(msg);
		connection.close();
	

	}

}
