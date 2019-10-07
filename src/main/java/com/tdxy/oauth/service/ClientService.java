package com.tdxy.oauth.service;

import com.tdxy.oauth.Constant;
import com.tdxy.oauth.common.RandomStringUtil;
import com.tdxy.oauth.redis.RedisUtil;
import com.tdxy.oauth.exception.UnknownClientException;
import com.tdxy.oauth.model.po.Client;
import com.tdxy.oauth.model.bo.User;
import com.tdxy.oauth.model.dao.ClientDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * 客户端服务
 *
 * @author Qug_
 */
@Service
public class ClientService {
    private final static Logger logger = LoggerFactory.getLogger(ClientService.class);
    /**
     * 客户端DAO层
     */
    private final ClientDao clientDao;

    /**
     * redis工具
     */
    private final RedisUtil redisUtil;

    @Autowired
    public ClientService(ClientDao clientDao, RedisUtil redisUtil) {
        this.clientDao = clientDao;
        this.redisUtil = redisUtil;
    }

    /**
     * 通过appid取得客户端
     *
     * @param appId appid
     * @return Client实体
     * @throws Exception 客户端异常
     */
    public Client getClient(String appId) throws UnknownClientException {
        Client client = clientDao.findByAppId(appId);
        if (client == null) {
            throw new UnknownClientException("无效的客户端");
        }
        return client;
    }

    /**
     * 下发临时的授权码
     *
     * @param appId appid
     * @param user  授权的用户
     * @return 10位字符串
     */
    public String getCode(String appId, User user) {
        String prefix = Constant.Code.PREFIX + appId + "_" + user.getIdentity();
        String code = (String) redisUtil.get(prefix);
        if (Objects.nonNull(code)) {
            return code;
        }
        code = RandomStringUtil.getNonceStr();
        HashMap<String, Object> data = new HashMap<>(4);
        data.put(prefix, code);
        data.put(code, user);
        Boolean success = redisUtil.set(data, Constant.Code.EXPIRE_TIME_SEC);
        if (!success) {
            logger.warn("Get code failure, [{} -> {}, {} -> {}]", prefix, code, code, user);
        }
        return code;
    }

}
