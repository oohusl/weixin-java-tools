package me.chanjar.weixin.mp.api;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * 基于redis的微信配置provider
 *
 * @author oohusl
 */
public class WxMpRedisConfigStorage extends WxMpInMemoryConfigStorage {

    private static String ACCESS_TOKEN_KEY = "AT:";

    private static String JSAPI_TICKET_KEY = "JT:";

    private RedisTemplate redisTemplate;

    @Override
    public String getAccessToken() {
        return (String) redisTemplate.opsForValue().get(ACCESS_TOKEN_KEY + getAppId());
    }

    @Override
    public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
        this.expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000l;
        redisTemplate.opsForValue().set(ACCESS_TOKEN_KEY + getAppId(), accessToken);
    }

    @Override
    public String getJsapiTicket() {
        return (String) redisTemplate.opsForValue().get(JSAPI_TICKET_KEY + getAppId());
    }


    @Override
    public synchronized void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
        this.jsapiTicketExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000l;
        redisTemplate.opsForValue().set(JSAPI_TICKET_KEY + getAppId(), jsapiTicket);
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
