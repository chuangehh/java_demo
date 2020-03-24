package com.lcc.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;
import java.io.IOException;

/**
 * 队列消息消费者
 *
 * 两种消费方式
 * 1.同步阻塞方法 receive()
 * 2.异步非阻塞方式(注册监听器,onMessage()), 监听器调用另一个线程处理
 */
public class JmsConsumer {
	public static final String DEFAULT_BROKER_URL = "tcp://hadoop100:61616";
	public static final String QUEUE_NAME = "lcc_queue";

	public static void main(String[] args) throws JMSException, IOException {
		System.out.println("消费者1");
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
		Connection connection = factory.createConnection("admin", "admin");
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue(QUEUE_NAME);
		MessageConsumer consumer = session.createConsumer(queue);

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
