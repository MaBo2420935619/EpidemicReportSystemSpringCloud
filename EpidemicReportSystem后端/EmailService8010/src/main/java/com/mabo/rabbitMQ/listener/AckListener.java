package com.mabo.rabbitMQ.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Date;

@Component
public class AckListener implements ChannelAwareMessageListener {
    @RabbitListener(queuesToDeclare = @Queue("email_order"))
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            Date date = new Date();
            System.out.println("手动确认"+date.toString());
            System.out.println(message.getBody());
            System.out.println(channel);
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            channel.basicNack(deliveryTag,true,true);
        }
    }
}
