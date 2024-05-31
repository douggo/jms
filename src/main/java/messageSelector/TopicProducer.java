package messageSelector;

import modelo.Pedido;
import modelo.PedidoFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;
import java.io.StringWriter;

public class TopicProducer {

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();

        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer((Destination) context.lookup("loja"));

        Pedido pedido = new PedidoFactory().geraPedidoComValores();

//        StringWriter writer = new StringWriter();
//        JAXB.marshal(pedido, writer);
//        String xml = writer.toString();
//        System.out.println(xml);

        Message message = session.createObjectMessage(pedido);
        message.setBooleanProperty("ebook", false);
        producer.send(message);

        session.close();
        connection.close();
        context.close();
    }

}
