package com.blog.admin.core.shiro;

import lombok.extern.log4j.Log4j2;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/3/31 1:11
 * @Description:权限缓存控制器
 */
@Log4j2
public class RedisCache<K,V> implements Cache<K,V> {

    private static final String SHIRO_FUNCTION="shiro:function:";

    private RedisTemplate<String,V> redisTemplate;

    public RedisCache(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获得缓存对象
     * @param key
     * @return
     * @throws CacheException
     */
    @Override
    public V get(K key) throws CacheException {
        try {
            V val = redisTemplate.boundValueOps(SHIRO_FUNCTION+key).get();
            if(null!=val){
                return val;
            }
        }catch (Exception e){
            log.error(this.getClass(),e);
        }
        return null;
    }

    /**
     * 将权限加入缓存
     * @param key
     * @param value
     * @return
     * @throws CacheException
     */
    @Override
    public V put(K key, V value) throws CacheException {
        try {
            redisTemplate.boundValueOps(SHIRO_FUNCTION+key).set(value);
            return value;
        }catch (Exception e){
            log.error(this.getClass(),e);
        }
        return null;
    }

    /**
     * 从缓存中删除权限
     * @param key
     * @return
     * @throws CacheException
     */
    @Override
    public V remove(K key) throws CacheException {
        try {
            if (null!=key&&!"".equals(key)) {
                V v = redisTemplate.opsForValue().get(SHIRO_FUNCTION+key);
                redisTemplate.opsForValue().getOperations().delete(SHIRO_FUNCTION+key);
                return v;
            }
        }catch (Exception e){
            log.error(this.getClass(),e);
        }
        return null;
    }

    /**
     * 清空权限缓存
     * @throws CacheException
     */
    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(SHIRO_FUNCTION.substring(0,SHIRO_FUNCTION.length()-1));
    }

    /**
     * 权限数量
     * @return
     */
    @Override
    public int size() {
        return redisTemplate.boundListOps(SHIRO_FUNCTION.substring(0,SHIRO_FUNCTION.length()-1)).size().intValue();
    }

    /**
     * 所有的权限key
     * @return
     */
    @Override
    public Set<K> keys() {
        return (Set<K>) redisTemplate.keys(SHIRO_FUNCTION+"*");
    }

    /**
     * 所有的权限value
     * @return
     */
    @Override
    public Collection<V> values() {
        return (Collection<V>) redisTemplate.boundHashOps(SHIRO_FUNCTION.substring(0,SHIRO_FUNCTION.length()-1)).values();
    }
}
