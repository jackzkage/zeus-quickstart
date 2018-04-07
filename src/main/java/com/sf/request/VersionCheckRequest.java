package com.sf.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import java.io.Serializable;

/**
 * @author zhonglj
 * @date 2017/12/1
 */
@Data
public class VersionCheckRequest implements Serializable {

    @NotEmpty(message = "{curversion.not.empty}")
    @ApiModelProperty(value = "当前的版本号", required = true)
    String curVersion;

    @Max(value = 10000, message = "当前的缓存号不能大于10000")
    @ApiModelProperty(value = "当前的缓存号", required = true)
    Integer curCacheNo;

}
