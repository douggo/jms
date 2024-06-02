package deadLetterQueue;

import org.apache.activemq.broker.region.policy.ConstantPendingMessageLimitStrategy;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class QueueConsumer {

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();

        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();

        connection.start();

        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        MessageConsumer consumer = session.createConsumer((Destination) context.lookup("DLQ"));

        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println(textMessage.getText());
                session.commit();
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        });
        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        context.close();
    }

}
