package messageSelector;

import modelo.Pedido;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class TopicConsumer {

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();

        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        connection.setClientID("estoque");

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createDurableSubscriber((Topic) context.lookup("loja"),
                "assinatura",
                "ebook is null OR ebook = false",
                false
        );

        consumer.setMessageListener(message -> {
            ObjectMessage objectMessage = (ObjectMessage) message;
            try {
                Pedido pedido = (Pedido) objectMessage.getObject();
                System.out.println(pedido.getCodigo());
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
