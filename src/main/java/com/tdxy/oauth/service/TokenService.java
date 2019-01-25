package com.tdxy.oauth.service;

import com.tdxy.oauth.component.RedisUtil;
import com.tdxy.oauth.exception.IllegalClientException;
import com.tdxy.oauth.exception.InvalidCodeException;
import com.tdxy.oauth.exception.InvalidTokenException;
import com.tdxy.oauth.model.entity.Client;
import com.tdxy.oauth.model.entity.Token;
import com.tdxy.oauth.model.entity.User;
import com.tdxy.oauth.model.impl.ClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * token服务层
 *
 * @author Qug_
 */
@Service
public class TokenService {
    /**
     * 客户端DAO层
     */
    private ClientImpl clientImpl;

    /**
     * redis工具类
     */
    private RedisUtil redisUtil;

    @Autowired
    public TokenService(ClientImpl clientImpl, RedisUtil redisUtil) {
        this.clientImpl = clientImpl;
        this.redisUtil = redisUtil;
    }

    /**
     * 下发最终的Token信息
     *
     * @param client 应用
     * @param user   用户实体
     * @param time   租赁时间
     * @return Token实体
     */
    public Token getToken(Client client, User user, long time) {
        String accessToken = null;
        String uid = "access_token:" + client.getAppId() + "_" + user.getIdentity();
        // 如果之前生成过了就直接取出来返回
        if (this.redisUtil.hasKey(uid)) {
            accessToken = (String) this.redisUtil.get(uid);
        } else {
            // 生成随机的access_token
            accessToken = UUID.randomUUID().toString();
            // redis作为非关系型数据库，需要自己建立数据间联系
            this.redisUtil.set(uid, accessToken, time);
            this.redisUtil.set(accessToken, user, time);
        }
        // 构造Token实体
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setExpiresIn(time);
        token.setTokenType("bearer");
        token.setScope("read");
        return token;
    }

    /**
     * 检查授权码的有效性
     *
     * @param appId appid
     * @param code  授权码
     * @return 如果有效则返回授权的用户
     * @throws InvalidCodeException code异常
     */
    public User checkCode(String appId, String code) throws InvalidCodeException {
        if (!this.redisUtil.hasKey(code)) {
            throw new InvalidCodeException("无效的授权码");
        }
        User user = (User) this.redisUtil.get(code);
        String prefix = "code:" + appId + "_" + user.getIdentity();
        this.redisUtil.del(prefix);
        this.redisUtil.del(code);
        return user;
    }

    public Client checkClient(String appId, String appKey) throws IllegalClientException {
        // 依据密钥key来判断是否合法
        Client client = this.clientImpl.checkByAppKey(appId, appKey);
        if (client == null) {
            throw new IllegalClientException("非法客户端的请求");
        }
        return client;
    }

    /**
     * 通过access_token取得授权用户
     *
     * @param accessToken access_token
     * @return User实体
     * @throws InvalidTokenException 异常
     */
    public User getUserByToken(String accessToken) throws InvalidTokenException {
        if (this.redisUtil.hasKey(accessToken)) {
            return (User) this.redisUtil.get(accessToken);
        } else {
            throw new InvalidTokenException("无效的token");
        }
    }
}
