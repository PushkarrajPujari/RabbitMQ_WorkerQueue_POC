import com.rabbitmq.client.*;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * @Author : Pushkarraj Pujari
 * @Since : 03/09/2017
 */
public class Worker {
    /**
     * Stage 2 : Consumer of the worker queue
     * */
    private static String Queue_Name = "WorkerQueue";
    public static void main(String[] args) {
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            Connection connection = connectionFactory.newConnection(getInput("Enter Connection Name"));
            Channel channel = connection.createChannel();
            channel.queueDeclare(Queue_Name,false,false,false,null);
            System.out.println(" [*] Waiting for messages");
            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                    try {
                        String message = new String(body, "UTF-8");
                        int i = Integer.parseInt(message);
                        i = i +10;
                        System.out.println(" [x] Received message '" + message + "Calculated = "+i);
                        Thread.currentThread().sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            channel.basicConsume(Queue_Name,true,consumer);
            getInput("Press Enter To Stop program");
            channel.close();
            connection.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public static String  getInput(String string){
        System.out.println(string);
        return new Scanner(System.in).nextLine();
    }
}
