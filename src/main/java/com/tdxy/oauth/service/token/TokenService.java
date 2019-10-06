package com.tdxy.oauth.service.token;

import com.tdxy.oauth.Constant;
import com.tdxy.oauth.redis.RedisUtil;
import com.tdxy.oauth.exception.IllegalClientException;
import com.tdxy.oauth.exception.InvalidCodeException;
import com.tdxy.oauth.exception.InvalidTokenException;
import com.tdxy.oauth.model.po.Client;
import com.tdxy.oauth.model.bo.RefreshToken;
import com.tdxy.oauth.model.po.Token;
import com.tdxy.oauth.model.bo.User;
import com.tdxy.oauth.model.dao.ClientDao;
import com.tdxy.oauth.model.dao.RefreshTokenDao;
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
    private final ClientDao clientDao;

    /**
     * redis工具类
     */
    private final RedisUtil redisUtil;

    private final RefreshTokenDao refreshTokenDao;

    @Autowired
    public TokenService(ClientDao clientDao, RedisUtil redisUtil, RefreshTokenDao refreshTokenDao) {
        this.clientDao = clientDao;
        this.redisUtil = redisUtil;
        this.refreshTokenDao = refreshTokenDao;
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
        String accessToken, refreshToken;
        RefreshToken oldRefreshToken = this.refreshTokenDao.findByAppIdAndUser(client.getAppId(), user.getIdentity());
        // 生成随机的access_token
        accessToken = UUID.randomUUID().toString();
        this.redisUtil.set(accessToken, user, time);
        if (oldRefreshToken == null) {
            refreshToken = UUID.randomUUID().toString();
            RefreshToken newRefreshToken = new RefreshToken(refreshToken,
                    accessToken, client.getAppId(), user.getIdentity(), user.getRole());
            this.refreshTokenDao.addOne(newRefreshToken);
        } else {
            refreshToken = oldRefreshToken.getRefreshToken();
            if (this.redisUtil.hasKey(oldRefreshToken.getTokenId())) {
                this.redisUtil.del(oldRefreshToken.getTokenId());
            }
            this.refreshTokenDao.updateByTokenId(accessToken, refreshToken);
        }
        // 构造Token实体
        return new Token(accessToken, Constant.Token.TYPE, time, refreshToken, Constant.Token.SCOPE);
    }

    public Token refreshToken(String refreshToken, long time) throws InvalidTokenException {
        RefreshToken refresh = (refreshToken != null) ?
                this.refreshTokenDao.findTokenByRefreshToken(refreshToken) : null;
        String accessToken;
        if (refresh != null) {
            if (this.redisUtil.hasKey(refresh.getTokenId())) {
                this.redisUtil.del(refresh.getTokenId());
            }
            User user = new User(refresh.getUserRole(), refresh.getUserIdentity());
            accessToken = UUID.randomUUID().toString();
            this.redisUtil.set(accessToken, user, time);
            this.refreshTokenDao.updateByTokenId(accessToken, refreshToken);
        } else {
            throw new InvalidTokenException("无效的refresh_token");
        }
        return new Token(accessToken, Constant.Token.TYPE, time, refreshToken, Constant.Token.SCOPE);
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
        if (code == null || !this.redisUtil.hasKey(code)) {
            throw new InvalidCodeException("无效的授权码");
        }
        User user = (User) this.redisUtil.get(code);
        String prefix = Constant.Code.PREFIX + appId + "_" + user.getIdentity();
        this.redisUtil.del(prefix);
        this.redisUtil.del(code);
        return user;
    }

    public Client checkClient(String appId, String appKey) throws IllegalClientException {
        // 依据密钥key来判断是否合法
        Client client = this.clientDao.checkByAppKey(appId, appKey);
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
            throw new InvalidTokenException("无效的access_token");
        }
    }
}
