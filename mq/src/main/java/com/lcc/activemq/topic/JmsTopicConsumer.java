package com.lcc.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;
import java.io.IOException;

/**
 * Topic消息消费者
 */
public class JmsTopicConsumer {
	public static final String DEFAULT_BROKER_URL = "tcp://hadoop100:61616";
	public static final String TOPIC_NAME = "lcc_topic";

	public static void main(String[] args) throws JMSException, IOException {
		System.out.println(Thread.currentThread().getName() + " 2号消费者" );
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
		Connection connection = factory.createConnection("admin", "admin");
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination dest = session.createTopic(TOPIC_NAME);
		MessageConsumer consumer = session.createConsumer(dest);

		// 1.直接消费
		while (true) {
			Message receive = consumer.receive();
			if (receive instanceof ActiveMQTextMessage) {
				System.out.println(Thread.currentThread().getName() + " " );
				ActiveMQTextMessage textMessage = (ActiveMQTextMessage) receive;
				System.out.println("消费消息: " + textMessage.getText());
			}else{
				break;
			}
		}

		System.out.println(Thread.currentThread().getName() + " " );

		// 2.监听
//		consumer.setMessageListener(message -> {
//			if (message instanceof ActiveMQTextMessage) {
//				System.out.println(Thread.currentThread().getName() + " " );
//				ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
//				try {
//					System.out.println("消费消息: " + textMessage.getText());
//				} catch (JMSException e) {
//					e.printStackTrace();
//				}
//			}
//		});

		System.in.read();
		session.close();
		connection.close();
	}
}
