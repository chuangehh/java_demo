package com.lcc.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Topic消息生产者
 */
public class JmsTopicProduce {
	public static final String DEFAULT_BROKER_URL = "tcp://hadoop100:61616";
	public static final String TOPIC_NAME = "lcc_topic";

	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
		Connection connection = factory.createConnection("admin", "admin");
		connection.start();

		// 事物,签收
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination dest = session.createTopic(TOPIC_NAME);

		MessageProducer producer = session.createProducer(dest);
		for (int i = 1; i <= 10; i++) {
			producer.send(session.createTextMessage("hello activemq: " + i));
		}

		session.close();
		connection.close();
	}
}
