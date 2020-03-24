package com.lcc.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *
 */
public class JmsProduce {
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
		for (int i = 0; i < 10; i++) {
			producer.send(session.createTextMessage("hello activemq: " + i));
		}

		session.close();
		connection.close();
	}
}
