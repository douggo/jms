import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class TesteConsumidor {

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();

        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();

        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer((Destination) context.lookup("financeiro"));
        Message message = consumer.receive();
        System.out.println("Mensagem recebida: " + message);

        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        context.close();
    }

}
