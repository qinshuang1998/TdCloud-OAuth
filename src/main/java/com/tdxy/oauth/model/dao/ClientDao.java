package com.tdxy.oauth.model.dao;

import com.tdxy.oauth.model.po.Client;
import com.tdxy.oauth.model.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Qug_
 */
@Repository
public class ClientDao {
    /**
     * Mybatis_Client的mapper接口
     */
    @Autowired
    private ClientMapper clientMapper;

    /**
     * 以appid查询Client
     *
     * @param appId 客户端标识
     * @return Client实体
     */
    public Client findByAppId(String appId) {
        return clientMapper.selectByAppId(appId);
    }

    /**
     * 判断Client是否合法
     *
     * @param appId  应用标识
     * @param appKey 应用key
     * @return 成功时返回Client实体
     */
    public Client checkByAppKey(String appId, String appKey) {
        Client client = findByAppId(appId);
        if (client != null && appKey.equals(client.getAppKey())) {
            return client;
        }
        return null;
    }
}
