package com.mabo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;

/**
 * @Author: 潇哥
 * @DateTime: 2020/11/9 下午5:17
 * @Description: TODO
 */
@Configuration
public class RabbitmqConfig {


    public static final String TTL_QUEUE = "ttl_queue";

    public static final String TTL_EXCHANGE = "ttl_exchange";

    public static final String TTL_ROUTINGKEY = "ttl.#";

    // 死信队列配置
    public static final String DL_QUEUE = "dl_queue";
    public static final String DL_EXCHANGE = "dl.exchange";
    public static final String DL_KEY = "dl.key";



    /**
     * @Author mabo
     * @Description   ttl队列
     */

    // 声明QUEUE_INFORM_EMAIL队列
    @Bean(TTL_QUEUE)
    public Queue TTL_QUEUE(){
        HashMap<String, Object> args = new HashMap<>();
        //队列中消息的过期时间
        args.put("x-message-ttl",10000);
        //当消息变为死信后重新发送到指定死信交换机
        args.put("x-dead-letter-exchange",DL_EXCHANGE);
        //当死信到达死信交换机后，根据该路由键投递到指定的死信队列
        args.put("x-dead-letter-routing-key",DL_KEY);
        return new Queue(TTL_QUEUE,true,false,false,args);

    }

    // 声明交换机
    @Bean(TTL_EXCHANGE)
    public Exchange TTL_EXCHANGE(){
        // durable(true) 持久化, mq重启之后交换机还在
        return ExchangeBuilder.topicExchange(TTL_EXCHANGE).durable(true).build();
    }


    // ROUTINGKEY_EMAIL队列绑定交换机, 指定routingKey
    // @Qualifier注解给类成员变量使用时需要依赖@Autowired, 但是给方法参数可以单独使用
    @Bean
    public Binding BINDING_QUEUE_INFORM_TTL(@Qualifier(TTL_QUEUE) Queue queue, @Qualifier (TTL_EXCHANGE) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(TTL_ROUTINGKEY).noargs();
    }







}

