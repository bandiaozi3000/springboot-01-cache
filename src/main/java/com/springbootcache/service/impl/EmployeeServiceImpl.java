package com.springbootcache.service.impl;

import com.springbootcache.bean.Employee;
import com.springbootcache.dao.EmployeeMapper;
import com.springbootcache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;



@Service
@CacheConfig(cacheManager = "empRedisCacheManager")
//全局注解,即下面的方法统一缓存数据在emp这个缓存组件中
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;


    /**
     * CacheManager管理多个Cache组件,对缓存的真正CRUD操作在Cache组件中,没个缓存组件有自己的唯一名字
     * 几个属性:
     *     cacheNames/values:指定缓存组件的名字
     *     key:缓存数据使用的Key,可以用它来指定,默认是使用方法的参数值 1-方法的返回值
     *          编写SpEl: #id：参数id的值
     *     keyGenerator:key的生成器,可以自己指定key的生成器组件id
     *           key/keyGenerator二选一使用
     *     cacheManager:指定缓存管理器；或者cacheResolver指定获取解析器
     *     condition:指定符合条件的情况下才缓存
     *               condition="#id>0" condition="#a0>1" 第一个参数的值≥1的时候才进行缓存
     *     unless:否定缓存;当unless的指定条件为true时,方法的返回值就不会被缓存,可以获取到结果进行判断
     *             unless="#result==null"
     *             unless="#a0==2":如果第一个参数的值为2,那么不进行缓存
     *     sync:是否使用异步模式
     *
     *  原理:
     *      1.自动配置类:CacheAutoConfiguration
     *      2.缓存的配置类
     *      3.哪个配置类生效:SimpleCacheConfiguration
     *      4.给容器注册了一个CacheManager:ConcurrentCacheManager
     *      5.可以获取ConcurrentMapCache类型的缓存组件
     *
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(cacheNames = "emp")
    public Employee getEmployeeById(Integer id) {
        Employee employee = employeeMapper.getEmployeeById(id);
        return employee;
    }

    /**
     * @CachePut:既调用方法,又更新缓存数据
     * 修改了数据库的某个数据,同时更新缓存
     * 运行时机:
     *   1.先调用目标方法
     *   2.将目标方法的结果缓存起来
     *
     *  测试步骤：
     *    1.查询1号员工，查到的结果会放在缓存中
     *    2.以后查询还是之前的结果
     *    3.更新1号员工:
     *          方法的返回值也放进缓存：
     *              key：传入的employee对象,返回的employee对象
     *    4.查询1号员工？
     *          应该是更新后的员工：
     *              key="#employee.id":使用传入的参数的员工id
     *              key="#result.id":使用的是返回后的id
     *             @Cacheable的key是不能用#result
     *             原因:@Cacheable是在方法之前调用,所以不能用方法返回的结果值,@Cacheput则在方法调用之后执行
     *
     *      注意:要指定缓存key
     * @param employee
     */
    @Override
    @CachePut(cacheNames="emp",key="#employee.id")
    public Employee updateEmployee(Employee employee) {
        employeeMapper.updateEmployee(employee);
        return employee;
    }

    /**
     * @CacheEvict:缓存清除
     *     key:指定要清除这个缓存中的所有数据
     *     allEntries=true:指定清楚这个缓存中的所有数据
     *     beforeInvocation=false:缓存的清楚是否在方法之前执行
     *         默认代表缓存的清除操作是在方法之后执行，如果出现异常缓存就不会清除
     *      beforeInvocation=true:
     *          代表清除操作是在方法执行之前执行
     * @param id
     */
    @Override
    @CacheEvict(value="emp",key="#id")
    public void deleteEmployeeById(Integer id) {
        System.out.println("删除"+id+"号员工");
    }

    /**
     * @Caching 可定义复杂的缓存规则
      */
//   @Caching(
//           cacheable = {
//                   @Cacheable(value="emp",key="#id")
//           },
//           put={
//                   @CachePut(),
//                   @CachePut,
//           }
//   )
//    public void getEmployeeByLastName(String lastName){
//    }


}
