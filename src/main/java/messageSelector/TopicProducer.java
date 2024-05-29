package messageSelector;

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

        Message message = session.createTextMessage("<pedido><id>1</id><ebook>false</ebook></pedido");
        message.setBooleanProperty("ebook", false);
        producer.send(message);

        session.close();
        connection.close();
        context.close();
    }

}
