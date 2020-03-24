package com.lcc.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;
import java.io.IOException;

/**
 * 队列消息生产者
 */
public class JmsConsumer {
	public static final String DEFAULT_BROKER_URL = "tcp://hadoop100:61616";
	public static final String QUEUE_NAME = "lcc_queue";

	public static void main(String[] args) throws JMSException, IOException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
		Connection connection = factory.createConnection("admin", "admin");
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue(QUEUE_NAME);
		MessageConsumer consumer = session.createConsumer(queue);

		// 1.直接消费
//		while (true) {
//			Message receive = consumer.receive(200);
//			if (receive instanceof ActiveMQTextMessage) {
//				ActiveMQTextMessage textMessage = (ActiveMQTextMessage) receive;
//				System.out.println("消费消息: " + textMessage.getText());
//			}else{
//				break;
//			}
//		}

		// 2.监听
		consumer.setMessageListener(message -> {
			if (message instanceof ActiveMQTextMessage) {
				ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
				try {
					System.out.println("消费消息: " + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

		System.in.read();
		session.close();
		connection.close();
	}
}
