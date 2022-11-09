package com.example.cache.bo;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheManagerImpl implements ICacheManager {
    private static final Map<String, EntityCache> caches =new ConcurrentHashMap<String, EntityCache>();

    /**
     * 存入缓存
     * @param key
     * @param cache
     */
    @CachePut(value ="entitycache",keyGenerator= "idGenerator")
    @Override
    public void putCache(String key, EntityCache cache) {
        caches.put(key, cache);
    }

    /**存入缓存
     *
     * @param key
     * @param datas
     * @param timeOut
     */
    @Override
    @CachePut(value ="entitycache",keyGenerator= "idGenerator")
    public void putCache(String key, Object datas, long timeOut) {
        timeOut = timeOut > 0 ? timeOut : 0L;
        putCache(key, new EntityCache(datas, timeOut, System.currentTimeMillis()));
    }

    /**
     * 获取对应缓存
     * @param key
     * @return
     */
    @Override
    @Cacheable(value = "entitycache",keyGenerator= "idGenerator")
    public EntityCache getCacheByKey(String key) {
        if (this.isContains(key)) {
            return caches.get(key);
        }
        return null;
    }

    /**
     * 获取对应缓存
     * @param key
     * @return
     */
    @Override
    @Cacheable(value = "entitycache",keyGenerator= "idGenerator")
    public Object getCacheDataByKey(String key) {
        if (this.isContains(key)) {
            return caches.get(key).getDatas();
        }
        return null;
    }

    /**获取缓存
     *
     * @return
     */

    @Override
    @Cacheable(value = "entitycache",keyGenerator= "idGenerator")
    public Map<String, EntityCache> getCacheAll() {
        return caches;
    }

    /**
     * 判断是否在缓存中
     * @param key
     * @return
     */
    @Override
    public boolean isContains(String key) {
        return caches.containsKey(key);
    }

    /**
     * 清除所有缓存
     */
    @Override
    @CacheEvict(value = "entitycache",keyGenerator= "idGenerator")
    public void clearAll() {
        caches.clear();
    }

    /**
     * 清除对应缓存
     * @param key
     */
    @Override
    @CacheEvict(value = "entitycache",keyGenerator= "idGenerator")
    public void clearByKey(String key) {
        if (this.isContains(key)) {
            caches.remove(key);
        }
    }

    /**
     * 缓存是否超时失效
     * @param key
     * @return
     */
    @Override
    public boolean isTimeOut(String key) {
        if (!caches.containsKey(key)) {
            return true;
        }
        EntityCache cache = caches.get(key);
        long timeOut = cache.getTimeOut();
        long lastRefreshTime = cache.getLastRefeshTime();
        if (timeOut == 0 || System.currentTimeMillis() - lastRefreshTime >= timeOut) {
            return true;
        }
        return false;
    }

    /**
     * 获取所有key
     * @return
     */
    @Override
    @Cacheable(value = "entitycache",keyGenerator= "idGenerator")
    public Set<String> getAllKeys() {
        return caches.keySet();
    }

}