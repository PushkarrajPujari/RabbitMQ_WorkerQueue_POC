

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * @Author : Pushkarraj Pujari
 * @Since  : 03/09/2017
 */
public class Send{
    private static String Queue_Name = "WorkerQueue";
    private static String message;
    /**
     * Stage 1 - added dependencies and created a basic publisher same done in Hello world
     *
     * */
    public static void main(String[] args) {
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            Connection connection = connectionFactory.newConnection("WorkerQueueSender");
            Channel channel = connection.createChannel();
            channel.queueDeclare(Queue_Name,false,false,false,null);
            for(int i = 0 ;i < 100 ; i ++){
                message = String.valueOf(i);
                channel.basicPublish("",Queue_Name,null,message.getBytes());
                Thread.currentThread().sleep(1000);
            }
            channel.close();
            connection.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
