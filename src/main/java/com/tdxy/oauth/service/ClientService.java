package com.tdxy.oauth.service;

import com.tdxy.oauth.Constant;
import com.tdxy.oauth.common.RandomStringUtil;
import com.tdxy.oauth.redis.RedisUtil;
import com.tdxy.oauth.exception.UnknownClientException;
import com.tdxy.oauth.model.po.Client;
import com.tdxy.oauth.model.bo.User;
import com.tdxy.oauth.model.dao.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 客户端服务
 *
 * @author Qug_
 */
@Service
public class ClientService {
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
        RandomStringUtil utils = new RandomStringUtil();
        String prefix = Constant.Code.PREFIX + appId + "_" + user.getIdentity();
        String code = null;
        if (this.redisUtil.hasKey(prefix)) {
            code = (String) this.redisUtil.get(prefix);
        } else {
            code = utils.getNonceStr();
            this.redisUtil.set(prefix, code, Constant.Code.EXPIRE_TIME_SEC);
            this.redisUtil.set(code, user, Constant.Code.EXPIRE_TIME_SEC);
        }
        return code;
    }

}
