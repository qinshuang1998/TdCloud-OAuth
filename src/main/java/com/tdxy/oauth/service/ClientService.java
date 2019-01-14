package com.tdxy.oauth.service;

import com.tdxy.oauth.component.CustomUtils;
import com.tdxy.oauth.component.RedisUtil;
import com.tdxy.oauth.exception.UnknownClientException;
import com.tdxy.oauth.model.entity.Client;
import com.tdxy.oauth.model.entity.User;
import com.tdxy.oauth.model.impl.ClientImpl;
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
    private ClientImpl clientImpl;

    /**
     * redis工具
     */
    private RedisUtil redisUtil;

    @Autowired
    public ClientService(ClientImpl clientImpl, RedisUtil redisUtil) {
        this.clientImpl = clientImpl;
        this.redisUtil = redisUtil;
    }

    /**
     * 通过appid取得客户端
     * @param appId appid
     * @return Client实体
     * @throws Exception 客户端异常
     */
    public Client getClient(String appId) throws Exception {
        Client client = clientImpl.findByAppId(appId);
        if (client == null) {
            throw new UnknownClientException("无效的客户端");
        }
        return clientImpl.findByAppId(appId);
    }

    /**
     * 下发临时的授权码
     * @param appId appid
     * @param user 授权的用户
     * @return 10位字符串
     */
    public String getCode(String appId, User user) {
        CustomUtils utils = new CustomUtils();
        String prefix = "code:" + appId + "_" + user.getIdentity();
        String code = null;
        if (this.redisUtil.hasKey(prefix)) {
            code = (String) this.redisUtil.get(prefix);
        } else {
            code = utils.getNonceStr();
            this.redisUtil.set(prefix, code, 600);
            this.redisUtil.set(code, user, 600);
        }
        return code;
    }

}
