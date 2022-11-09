package com.example.cache.bo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Api(value = "测试")
@RestController
public class TestCache {
    Logger logger = Logger.getLogger("cacheLog");
    /**
     * 测试缓存和缓存失效
     */
    @ApiOperation(value = "",notes = "testCacheManager")
    @RequestMapping(value = "/testCacheManager",method = RequestMethod.POST)
    public void testCacheManager() {
        CacheManagerImpl cacheManagerImpl = new CacheManagerImpl();
        cacheManagerImpl.putCache("test", "zhao",  1000L);
        cacheManagerImpl.putCache("myTest", "linhai",  1000L);
        System.out.println(cacheManagerImpl.getCacheByKey("test").getDatas());
        System.out.println(cacheManagerImpl.getCacheByKey("myTest").getDatas());
        CacheListener cacheListener = new CacheListener(cacheManagerImpl);
        cacheListener.startListen();
//        logger.info("test:" + cacheManagerImpl.getCacheByKey("test").getDatas());
//        logger.info("myTest:" + cacheManagerImpl.getCacheByKey("myTest").getDatas());
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("test:" + cacheManagerImpl.getCacheByKey("test"));
        logger.info("myTest:" + cacheManagerImpl.getCacheByKey("myTest"));
    }

    /**
     * 测试线程安全
     */
    @ApiOperation(value = "",notes = "testThredSafe")
    @RequestMapping(value = "/testThredSafe",method = RequestMethod.POST)
    public void testThredSafe() {
        final String key = "thread";
        final CacheManagerImpl cacheManagerImpl = new CacheManagerImpl();
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            exec.execute(() -> {
                if (!cacheManagerImpl.isContains(key)) {
                    cacheManagerImpl.putCache(key, 1, 0);
                } else {
                    //因为+1和赋值操作不是原子性的，所以把它用synchronize块包起来
                    synchronized (cacheManagerImpl) {
                        int value = (Integer) cacheManagerImpl.getCacheDataByKey(key) + 1;
                        cacheManagerImpl.putCache(key,value , 0);
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
