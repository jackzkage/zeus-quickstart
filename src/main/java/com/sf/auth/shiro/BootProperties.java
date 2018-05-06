package com.sf.auth.shiro;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lijie.zh
 */
@Component
@Setter
@Getter
public class BootProperties {
    @Value("${auth.token.cacheType:1}")
    private String cacheType;

    @Value("${auth.token.expirateTime:3600}")
    private long expirateTime;

    @Value("${auth.token.enableCookies:false}")
    private boolean enableCookies;

    @Value("${system.enableFriendlyError:false}")
    private boolean enableFriendlyError;

}
