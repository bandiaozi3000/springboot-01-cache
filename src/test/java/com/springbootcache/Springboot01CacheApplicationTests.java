package com.springbootcache;

import com.springbootcache.bean.Employee;
import com.springbootcache.service.EmployeeService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

//import com.springbootcache.elasticsearch.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot01CacheApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    JestClient jestClient;

//    @Autowired
//    EmployeeRepository employeeRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void test(){
//        stringRedisTemplate.opsForValue().set("k2","v2");
//        System.out.println(stringRedisTemplate.opsForValue().get("k1"));
        Employee employee = employeeService.getEmployeeById(1);
        redisTemplate.opsForValue().set("emp1",employee);
    }

    /**
     * 给队列发送消息
     */
//    @Test
//    public void sendRabbitmq(){
//        Employee employee = new Employee();
//        employee.setId(2);
//        employee.setLastName("李四");
//        employee.setEmail("lisi@qq.com");
//        employee.setGender(1);
//        employee.setdId(2);
//        rabbitTemplate.convertAndSend("exchange.direct","atguigu",employee);
//    }

//    @Test
//    public void getRabbitmq(){
//        Employee employee =(Employee) rabbitTemplate.receiveAndConvert("atguigu");
//        System.out.println(employee);
//    }

    /**
     * 根据AmqpAdmin创建exchange,queue以及binding
     */
    @Test
    public void createExchange(){
        amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
        amqpAdmin.declareQueue(new Queue("amqpadmin.queue"));
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE,"amqpadmin.exchange","amqpadmin",null));
    }

    /**
     * 测试ElasticSearch
     */
    @Test
    public void testElasticSearch(){
        Employee employee = new Employee();
        employee.setId(2);
        employee.setLastName("李四");
        employee.setEmail("lisi@qq.com");
        employee.setGender(1);
        employee.setdId(2);
        Index index = new Index.Builder(employee).index("bandiaozi").type("emp").build();
        try {
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void searchElastic(){
        String json = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"lastName\" : \"李四\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        Search search = new Search.Builder(json).addIndex("bandiaozi").addType("emp").build();
        try {
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void  testSpringDataElasticSearch(){
//        Employee employee = new Employee();
//        employee.setId(2);
//        employee.setLastName("李四");
//        employee.setEmail("lisi@qq.com");
//        employee.setGender(1);
//        employee.setdId(2);
//        employeeRepository.index(employee);
//    }

}
