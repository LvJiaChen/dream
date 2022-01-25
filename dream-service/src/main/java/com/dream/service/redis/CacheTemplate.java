package com.dream.service.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lvxiaozuo
 * @date 2022/1/25 11:03
 */
@Service
@Slf4j
public class CacheTemplate {

    @Autowired
    private RedisService redisService;

    public CacheTemplate() {
    }

    public <T> T objectCacheWrapper(String key, Class<T> clazz, CacheObjectCallback callback) {
        T returnResult = null;

        try {
            returnResult = this.redisService.getHashToBean( key, clazz);
            if (returnResult != null) {
                log.info("获取cache " + key + " 成功");
                return returnResult;
            }
        } catch (Exception var8) {
            var8.printStackTrace();
            log.info("获取cache " + key + " 失败");
        }

        if (returnResult == null) {
            returnResult = callback.getLatestValue();
            if (returnResult != null) {
                try {
                    this.redisService.setBeanToHash(key, returnResult);
                } catch (Exception var7) {
                    var7.printStackTrace();
                    log.info("写入cache " + key + " 失败");
                }
                return returnResult;
            }
        }
        return null;
    }

/*    public <T> List<T> listCacheWrapper( String key, Class<T> clazz, CacheListCallback callback) {
        List returnResult = null;

        try {
            returnResult = this.redisService.getList(basePrefix, key, clazz);
            if (returnResult != null && returnResult.size() > 0) {
                log.info("获取cache " + key + " 成功");
                return returnResult;
            }
        } catch (Exception var8) {
            var8.printStackTrace();
            log.info("获取cache " + key + " 失败");
        }

        if (returnResult == null || returnResult.size() <= 0) {
            returnResult = callback.getLatestValues();
            if (returnResult != null && returnResult.size() > 0) {
                try {
                    this.redisService.set(basePrefix, key, returnResult);
                } catch (Exception var7) {
                    var7.printStackTrace();
                    log.info("写入cache " + key + " 失败");
                }

                return returnResult;
            }
        }

        return null;
    }*/


}
