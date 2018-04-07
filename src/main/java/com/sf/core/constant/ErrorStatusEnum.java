package com.sf.core.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhonglj
 */

public enum ErrorStatusEnum {

    DEFAULT_ERRROR("-1", "服务器异常"),
    SERVICE_CURRENTLY_UNAVAILABLE("901", "服务不可用"),
    INSUFFICIENT_ISV_PERMISSIONS("902", "开发者权限不足"),
    INSUFFICIENT_USER_PERMISSIONS("903", "用户权限不足"),
    SERVICE_CURRENTLY_NOTFOUND("904", "服务不存在"),
    HTTP_ACTION_NOT_ALLOWED("905", "HTTP请求方式被禁止"),
    INVALID_ENCODING("906", "编码错误"),
    FORBIDDEN_REQUEST("907", "请求被禁止"),
    METHOD_OBSOLETED("908", "服务已经作废"),
    BUSINESS_LOGIC_ERROR("909", "业务逻辑出错"),
    UPLOAD_FAIL("910", "图片上传失败"),
    MISSING_SESSION("920", "缺少 sessionId 参数 "),
    INVALID_SESSION("921", "会话不存在或者已过期"),
    MISSING_APP_KEY("922", "缺少 appKey 参数"),
    INVALID_APP_KEY("923", "无效的 appKey 参数"),
    MISSING_SIGNATURE("924", "缺少签名参数"),
    INVALID_SIGNATURE("925", "无效签名"),
    MISSING_METHOD("926", "缺少方法名参数"),
    INVALID_METHOD("927", "不存在的方法名 "),
    MISSING_VERSION("928", "缺少版本参数"),
    INVALID_VERSION("929", "非法的版本参数"),
    UNSUPPORTED_VERSION("930", "不支持的版本号"),
    INVALID_FORMAT("931", "无效报文格式类型"),
    MISSING_REQUIRED_ARGUMENTS("932", "缺少必选参数"),
    INVALID_ARGUMENTS("933", "非法的参数"),
    EXCEED_USER_INVOKE_LIMITED("934", "用户调用服务的次数超限"),
    EXCEED_SESSION_INVOKE_LIMITED("935", "会话调用服务的次数超限"),
    EXCEED_APP_INVOKE_LIMITED("936", "应用调用服务的次数超限"),
    EXCEED_APP_INVOKE_FREQUENCY_LIMITED("937", "应用调用服务的频率超限"),
    FAILD_DECRYPT("938", "解密失败");

    /**
     * 值
     */
    private String value;
    /**
     * 中文描述
     */
    private String text;

    ErrorStatusEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * @return 当前枚举对象的值。
     */
    public String getValue() {
        return value;
    }

    /**
     * @return 当前状态的中文描述。
     */
    public String getText() {
        return text;
    }

    /**
     * 根据值获取枚举对象。
     *
     * @param status 值
     * @return 枚举对象
     */
    public static ErrorStatusEnum getInstance(String status) {
        ErrorStatusEnum[] allStatus = ErrorStatusEnum.values();
        for (ErrorStatusEnum ws : allStatus) {
            if (ws.getValue().equalsIgnoreCase(status)) {
                return ws;
            }
        }
        throw new IllegalArgumentException("值非法，没有符合的枚举对象");
    }


    public static List<Map> getAll() {
        List<Map> ls = new ArrayList<>();

        for (ErrorStatusEnum et : ErrorStatusEnum.values()) {
            Map<String, Object> m = new HashMap<>();
            m.put("value", et.value);
            m.put("text", et.text);
            ls.add(m);
        }
        return ls;
    }

}
