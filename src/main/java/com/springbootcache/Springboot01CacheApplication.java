package com.springbootcache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 快速体验缓存:
 *     1. 开启基于缓存的注解: @EnableCaching
 *     2. 标注缓存注解即可
 *            @Cacheable
 *            @CacheEvict
 *            @cachePut
 *
 *
 *  整合redis:
 *     1.安装reids，使用docker
 *     2.引入redis的启动器
 *     3.配置redis
 *     4.测试缓存:
 *           原理:CacheManager===cache 缓存组件来实际给缓存中存取数据
 *           1).引入redis的starter,容器中保存的是RedisCacheManager
 *           2).RedisCacheManager帮我们创建RedisCache来作为缓存组件,RedisCache通过操作redis缓存数据
 *           3).RedisTemplate<Object,Object>是使用Jdk的序列化机制
 *           4).自定义CacheManager
 *                注意:序列化器针对不同对象,所以不同对象的cacheManager要分别指定,可在service实现层类上家注解@CacheConf(cacheManager="")来指定
 *                     也可以在单个方法上添加该注解
 *
 *  整合RabbitMq:
 *     自动配置:
 *       1.RabbitAutoConfiguration
 *       2.有自动配置了连接工厂ConnectionFactory
 *       3.RabbitProperties封装了RabbitMq的配置
 *       4.RabbitmqTemplate:给RabbitMQ发送和接收消息
 *       5.AmqpAdmin:RabbitMQ系统管理功能组件
 *            AmqpAdmin:创建和删除 Queue,Exchange,Binding
 *       6.开启RabbitMQ监听:添加注解@RabbitListener,同时主程序添加注解@EnableRabbit
 *
 *  整合Elasticsearch:
 *      SpringBoot默认支持两种技术来和ES交互:
 *      1.Jest(默认不生效)
 *        需要导入jest的工具包(io.searchbox.client.JestClient)
 *      2.SpringData ElasticSearch[ES版本有可能不合适]
 *           版本适配说明:https://github.com/spring-projets/spring-data-elasticsearch
 *           如果版本不适配:2.4.6
 *                1).升级SpringBoot版本
 *                2).安装对应版本的ES
 *           1).Client节点信息clusterNodes:clusterName
 *           2).ElasticsearchTemplate操作es
 *           3).编写一个ElasticsearchRepository的子接口来操作ES;
 *           两种用法:
 *              1).编写一个ElasticsearchRepository
 *
 *
 */
@SpringBootApplication
@MapperScan("com.springcache.dao")
@EnableCaching
@EnableRabbit
public class Springboot01CacheApplication {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(Springboot01CacheApplication.class, args);
    }

}
