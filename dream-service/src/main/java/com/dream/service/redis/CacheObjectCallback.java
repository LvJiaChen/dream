package com.dream.service.redis;

import java.util.List;

public interface CacheObjectCallback {
    <T> T getLatestValue();
}
