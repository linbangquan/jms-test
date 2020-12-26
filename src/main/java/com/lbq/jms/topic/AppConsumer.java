package com.lbq.jms.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class AppConsumer {

	private static final String url = "tcp://127.0.0.1:61616";
	private static final String topicName = "topic-test";
	
	public static void main(String[] args) throws JMSException {
		System.out.println("--------------------------------------");
		// 1.创建ConnectionFactory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		//org.apache.activemq.ActiveMQConnectionFactory@543c6f6d
		
		//2.创建Connection
		Connection connection = connectionFactory.createConnection();
		//ActiveMQConnection {id=ID:DESKTOP-4C9966B-49955-1560067900619-1:1,clientId=null,started=false}
		
		//3.启动连接
		connection.start();
		
		//4.创建会话
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//ActiveMQSession {id=ID:DESKTOP-4C9966B-49955-1560067900619-1:1:1,started=true} java.lang.Object@7c729a55
		
		//5.创建一个目标
		Destination destination = session.createTopic(topicName);
		//topic://topic-test
		
		//6.创建一个消费者
		MessageConsumer consumer = session.createConsumer(destination);
		//ActiveMQMessageConsumer { value=ID:DESKTOP-4C9966B-49955-1560067900619-1:1:1:1, started=true }
		
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("接收消息：" + textMessage.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		
		//9.关闭连接
		//connection.close();
	}

}
