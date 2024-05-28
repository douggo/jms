package topics;

import javax.jms.*;
import javax.naming.InitialContext;

public class TopicProducer {

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();

        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer((Destination) context.lookup("loja"));

        Message message = session.createTextMessage("Testando criação de mensagem!");
        producer.send(message);

        session.close();
        connection.close();
        context.close();
    }

}
