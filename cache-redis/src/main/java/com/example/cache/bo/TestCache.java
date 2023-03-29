package com.example.cache.bo;


import com.alibaba.fastjson2.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.jedis.JedisUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

@Api(value = "测试")
@RestController
@Slf4j
public class TestCache {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ICacheManager cacheManager;

    @Autowired
    private UserService userService;

    @Autowired
    RedisTemplate<String, Serializable> redisCacheTemplate;

    @Value("${spring.redis.port}")
     private String serverPort;


    Logger logger = Logger.getLogger("cacheLog");

    private final Lock lock = new ReentrantLock();

    String goodsKey = "zhaolinhai";

    private static final String REDIS_LOCK = "redis_lock";

    @ApiOperation(value = "redisson分布式1",tags = "RedLock分布式锁1")
    @RequestMapping(value = "/redisLocks",method = RequestMethod.POST)
    public Object redisLocks() throws InterruptedException {
        String redisKey = UUID.randomUUID().toString() + Thread.currentThread().getName();
        RLock redisLock=redissonClient.getLock(REDIS_LOCK);
        redisLock.lock();
        try {
            // 获取库存数量
            String goods = stringRedisTemplate.opsForValue().get(goodsKey);
            int goodsNum = goods == null ? 0: Integer.parseInt(goods);
            if (goodsNum > 0){
                int realNum = goodsNum - 1;
                stringRedisTemplate.opsForValue().set(goodsKey,String.valueOf(realNum));
                System.out.println("成功买到商品，库存剩余：" + realNum + "件" + "\t 服务端口为：" + serverPort);

                return "成功买到商品，库存剩余：" + realNum + "件" + "\t 服务端口为：" + serverPort;
            }else {
                System.out.println("商品已售罄，欢迎下次光临!" + "\t 服务端口为：" + serverPort);
            }
            return "商品已售罄，欢迎下次光临!" + "\t 服务端口为：" + serverPort;
        } finally {
            redisLock.unlock();
            System.out.println("解锁成功");
        }
    }

//    @ApiOperation(value = "redisson分布式2",tags = "RedLock分布式锁2")
//    @RequestMapping(value = "/redisLockes",method = RequestMethod.POST)
//    public Object redisLockes(){
//        String redisKey = UUID.randomUUID().toString() + Thread.currentThread().getName();
//        RLock redisLock=redissonClient.getLock(REDIS_LOCK);
//        redisLock.lock();
//        try {
//            // 获取库存数量
//            String goods = stringRedisTemplate.opsForValue().get(goodsKey);
//            int goodsNum = goods == null ? 0: Integer.parseInt(goods);
//            if (goodsNum > 0){
//                int realNum = goodsNum - 1;
//                stringRedisTemplate.opsForValue().set(goodsKey,String.valueOf(realNum));
//                System.out.println("成功买到商品，库存剩余：" + realNum + "件" + "\t 服务端口为：" + serverPort);
//
//                return "成功买到商品，库存剩余：" + realNum + "件" + "\t 服务端口为：" + serverPort;
//            }else {
//                System.out.println("商品已售罄，欢迎下次光临!" + "\t 服务端口为：" + serverPort);
//            }
//            return "商品已售罄，欢迎下次光临!" + "\t 服务端口为：" + serverPort;
//        } finally {
//            redisLock.unlock();
//            System.out.println("解锁成功");
//        }
//    }

    @ApiOperation(value = "基于redis",tags ="单体架构下加锁" )
    @RequestMapping(value = "/re",method = RequestMethod.POST)
    public Object redisLock(){
        synchronized (this) {
            // 获取库存数量
            String goods = stringRedisTemplate.opsForValue().get(goodsKey);
            int goodsNum =goods==null? 0: Integer.parseInt(goods);
            if (goodsNum > 0){
                int realNum = goodsNum - 1;
                stringRedisTemplate.opsForValue().set(goodsKey,String.valueOf(realNum));
                System.out.println("成功买到商品，库存剩余：" + realNum + "件" + "\t 服务端口为：" + serverPort);
                return "成功买到商品，库存剩余：" + realNum + "件" + "\t 服务端口为：" + serverPort;
            }else {
                System.out.println("商品已售罄，欢迎下次光临!" + "\t 服务端口为：" + serverPort);
            }
            return "商品已售罄，欢迎下次光临!" + "\t 服务端口为：" + serverPort;
        }
    }

    @ApiOperation(value = "setSort",tags = "setSort的测试")
    @RequestMapping(value = "/setSort",method = RequestMethod.POST)
    public Object setSort() {

        Set<ZSetOperations.TypedTuple<String>> var = new HashSet<>();
        ZSetOperations.TypedTuple<String> tuple = new Operations("6",10);
        ZSetOperations.TypedTuple<String> tuple1 = new Operations("7",11);
        var.add(tuple);
        var.add(tuple1);
        System.out.println(stringRedisTemplate.opsForZSet().range("5",1,2));
       return stringRedisTemplate.opsForZSet().add("5",var);

    }

    @ApiOperation(value = "set",tags="set的使用")
    @RequestMapping(value = "/set",method = RequestMethod.POST)
    public Object set(){
        Test test=new Test();
        test.setId("3");
        test.setName("z");
        test.setCore("1");
         stringRedisTemplate.opsForSet().add("3",
                JSON.toJSONString(test),"7",JSON.toJSONString(new Test("4","l","8")));
       return stringRedisTemplate.opsForSet().members("3");
    }

    @ApiOperation(value ="删除Key",tags = "删除数据")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Object deleteKey(@RequestParam String key,@RequestBody Datas datas){
        try {
            stringRedisTemplate.opsForList().remove(datas.getKey(),datas.getCount(),datas.getValue());
            stringRedisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;

    }

    @ApiOperation(value = "查询",tags = "查询数据")
    @RequestMapping(value ="/select",method = RequestMethod.POST)
    public Object selectData(@RequestParam String key,
                             @RequestParam Long begin,
                             @RequestParam Long end){
        try {
            return stringRedisTemplate.opsForList().range(key,begin,end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @ApiOperation(value = "存储list",tags = "存入数据")
    @RequestMapping(value ="/list",method = RequestMethod.POST)
    public  Object saveList(){
        List<Test> testList=new ArrayList<>();
        Test test=new Test();
        test.setId("1");
        test.setName("z");
        test.setCore("1");
        testList.add(test);
        Test test1=new Test();
        test1.setId("1");
        test1.setName("l");
        test1.setCore("1324");
        testList.add(test1);
        testList.forEach(test2 -> {
            stringRedisTemplate.opsForList().rightPush(test2.getId(), JSON.toJSONString(testList));
            log.info("当前存储对象list：{}", stringRedisTemplate.opsForList()
                    .rightPush(test2.getId(), JSON.toJSONString(test2)));
            System.out.println(stringRedisTemplate.opsForList().range("6",0,3));
        });
        return testList;
    }

    @ApiOperation(value = "测试",tags = "存入数据")
    @RequestMapping(value ="/cun",method = RequestMethod.POST)
    public Object Test(@RequestParam String key){
        try {
//            return userService.save(test);
            Test test1=(Test)redisCacheTemplate.opsForValue().get(key);
            log.info("当前获取对象：{}",test1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 测试缓存和缓存失效
     */
    @ApiOperation(value = "",notes = "testCacheManager")
    @RequestMapping(value = "/testCacheManager",method = RequestMethod.POST)
    public void testCacheManager(@RequestParam String key) {
//        stringRedisTemplate.opsForValue().set("123","zhaolinhai");
        System.out.println(stringRedisTemplate.opsForValue().get(key));
        //String格式的计入redis
        ValueOperations<String, Serializable> valueOperations=redisCacheTemplate.opsForValue();
//                valueOperations.set("zhao","linhai");
        redisCacheTemplate.opsForValue().set("456", "zhaolinhai");
        redisCacheTemplate.opsForValue().set("8910", new Test("1","zhaolinhai","98"));
        log.info("结果:{}",redisCacheTemplate.opsForValue().get("8910"));
//        EntityCache entityCache = (EntityCache) redisCacheTemplate.opsForValue().get("456");
        log.info("结果:{}",redisCacheTemplate.opsForValue().get("456"));
        String str= (String) valueOperations.get("zhao");
        //删除key
        Boolean result=redisCacheTemplate.delete("123");
        System.out.println("str:"+str);
        System.out.println("result:"+result);
        System.out.println(str);
        CacheManagerImpl cacheManagerImpl = new CacheManagerImpl();
        cacheManagerImpl.putCache("test","zhao",10L);
        cacheManagerImpl.putCache("myTest","linhai",10L);
        System.out.println(cacheManagerImpl.getCacheByKey("test").getDatas());
        System.out.println(cacheManagerImpl.getCacheByKey("myTest").getDatas());
        logger.info("test:" + cacheManagerImpl.getCacheByKey("test").getDatas());
        logger.info("myTest:" + cacheManagerImpl.getCacheByKey("myTest").getDatas());
        CacheListener cacheListener = new CacheListener(cacheManagerImpl);
        cacheListener.startListen();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        logger.info("test:" + cacheManagerImpl.getCacheByKey("test"));
//        logger.info("myTest:" + cacheManagerImpl.getCacheByKey("myTest"));
    }

    /**
     * 测试线程安全
     */
    @ApiOperation(value = "线程安全测试",notes = "testThredSafe")
    @RequestMapping(value = "/testThredSafe",method = RequestMethod.POST)
    public void testThredSafe() {
        final String key = "thread";
        final CacheManagerImpl cacheManagerImpl = new CacheManagerImpl();
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    if (!cacheManagerImpl.isContains(key)) {
                        cacheManagerImpl.putCache(key, 1, 0);
                    } else {
                        //因为+1和赋值操作不是原子性的，所以把它用synchronize块包起来
                        synchronized (cacheManagerImpl) {
                            int value = (Integer) cacheManagerImpl.getCacheDataByKey(key) + 1;
                            cacheManagerImpl.putCache(key,value , 0);
                        }
                    }
                }
            });
        }
        exec.shutdown();
        try {
            exec.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        logger.info(cacheManagerImpl.getCacheDataByKey(key).toString());
    }

}
