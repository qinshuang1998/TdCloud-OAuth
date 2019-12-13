package com.tdxy.oauth.service.token;

import com.tdxy.oauth.common.ApplicationContextUtil;
import com.tdxy.oauth.exception.GrantTypeException;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Adapter调度器
 */
public class DispatcherAdapter {
    private static List<TokenHandlerAdapter> handler = new ArrayList<>();

    static {
        ApplicationContext ac = ApplicationContextUtil.getApplicationContext();
        Map<String, TokenHandlerAdapter> h = ac.getBeansOfType(TokenHandlerAdapter.class);
        if (!h.isEmpty()) {
            handler.addAll(h.values());
        }
    }

    public static TokenHandlerAdapter getAdapter(String grantType) throws GrantTypeException {
        for (TokenHandlerAdapter t : handler) {
            if (t.support(grantType)) {
                return t;
            }
        }
        throw new GrantTypeException("无效的grant_type");
    }
}
