package com.sf.auth.shiro;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "shiro.token")
@Setter
@Getter
public class BootProperties {

    private String cacheType;

    private String key;

    private long expirateTime;


}
