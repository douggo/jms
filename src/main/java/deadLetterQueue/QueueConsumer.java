package deadLetterQueue;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class QueueConsumer {

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();

        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer((Destination) context.lookup("DLQ"));

        consumer.setMessageListener(System.out::println);
        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        context.close();
    }

}
