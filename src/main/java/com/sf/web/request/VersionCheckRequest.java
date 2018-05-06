package com.sf.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lijie.zh
 */
@Data
public class VersionCheckRequest implements Serializable {

    @NotBlank(message = "{curversion.not.empty}")
    @ApiModelProperty(value = "当前的版本号", required = true)
    String curVersion;

    @Max(value = 10000, message = "当前的缓存号不能大于10000")
    @ApiModelProperty(value = "当前的缓存号", required = true)
    Integer curCacheNo;

}
