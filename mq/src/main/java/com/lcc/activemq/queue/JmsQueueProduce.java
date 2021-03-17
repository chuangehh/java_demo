package com.lcc.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 队列消息生产者
 * <p>
 * 1.每条消息只能有一条消息
 * 2.生产者与消费者没有时间上的相关性,无论消费者是否在线,消息都可以被提取
 * 3.不会重复消费已有的消息
 *
 *
 * JMS开发步骤:
 * 1.创建工厂
 * 2.工厂创建连接
 * 3.连接创建Session
 * 4.Session创建 目的地(Topic,Queue)
 * 5.Session创建 消费者/生产者
 * 6.Session创建 消息
 * 7.发送消息/消费消息
 * 8.关闭相关资源
 */
public class JmsQueueProduce {
	public static final String DEFAULT_BROKER_URL = "tcp://hadoop100:61616";
	public static final String QUEUE_NAME = "lcc_queue";

	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
		Connection connection = factory.createConnection("admin", "admin");
		connection.start();

		// 事物,签收
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue(QUEUE_NAME);

		MessageProducer producer = session.createProducer(queue);
		for (int i = 1; i <= 10; i++) {
			producer.send(session.createTextMessage("hello activemq: " + i));
		}

		session.close();
		connection.close();
	}
}
