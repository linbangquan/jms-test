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

public class AppConsumer2 {

	private static final String url = "tcp://127.0.0.1:61616";
	private static final String topicName = "topic-test";
	//ThreadLocal<MessageConsumer> threadLocal = new ThreadLocal<>();
	public static void main(String[] args) throws JMSException, InterruptedException {
		ThreadLocal<MessageConsumer> threadLocal = new ThreadLocal<>();
		System.out.println("--------------------------------------");
		// 1.创建ConnectionFactory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		
		//2.创建Connection
		Connection connection = connectionFactory.createConnection();
		
		//3.启动连接
		connection.start();
		
		//4.创建会话
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		System.out.println("sync start listen mq message >>>>>>>>>>>>>");
		
		//5.创建一个目标
		Destination destination = session.createTopic(topicName);
		
		MessageConsumer consumer = null;
        if(threadLocal.get()!=null){
            consumer = threadLocal.get();
        }else{
            consumer = session.createConsumer(destination);
            threadLocal.set(consumer);
        }
        while(true){
            Thread.sleep(1000);
            //MapMessage msg = (MapMessage) consumer.receive();
            TextMessage msg = (TextMessage) consumer.receive();
            if(msg!=null) {
                msg.acknowledge();
                System.out.println(Thread.currentThread().getName()+": Consumer:我是消费者，我正在消费 : "+ msg.getJMSMessageID()+",接收消息2：" + msg.getText());
                //System.out.println("action : "+ msg.getString("action"));
            }else {
                break;
            }
        }
        
		//6.创建一个消费者
//		MessageConsumer consumer = session.createConsumer(destination);
//		
//		consumer.setMessageListener(new MessageListener() {
//
//			@Override
//			public void onMessage(Message message) {
//				TextMessage textMessage = (TextMessage) message;
//				try {
//					System.out.println("接收消息2：" + textMessage.getText());
//				} catch (JMSException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			
//		});
		
		
		//9.关闭连接
		//connection.close();
	}

}
