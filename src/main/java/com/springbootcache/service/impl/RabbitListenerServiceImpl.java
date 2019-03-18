package com.springbootcache.service.impl;

/**
 * 注意:此时监听器只能接收atguigu队列中的Employee对象,若队列存储了其他对象,此时会报类型转换异常
 *      可使用如下参数:Message
 */
//@Service
//public class RabbitListenerServiceImpl implements RabbitListenerService {
//
//    @Override
//    @RabbitListener(queues = "atguigu")
//    public void listenRabbit(Message message) {
//        System.out.println(message);
//    }
//}
