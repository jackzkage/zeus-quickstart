package com.sf.web.response;

import com.sf.core.mvc.AbstractResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author lijie.zh
 */
@Builder
@Data
public class VersionCheckResponse  extends AbstractResponse {

    @ApiModelProperty(value = "发现新版本", notes = "为空表示没有新版本")
    String findNewVersion;

    @ApiModelProperty(value = "是否强制更新")
    Boolean isForceUpdate;

    @ApiModelProperty(value = "新版本更新内容")
    String updatedContent;

}
