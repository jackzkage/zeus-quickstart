package com.sf.core.constant;


/**
 * @author lijie.zh
 */

public enum ResultStatusEnum {
    SUCCESS("0", "成功"),
    ERROR(ErrorStatusEnum.DEFAULT_ERRROR.getValue(), ErrorStatusEnum.DEFAULT_ERRROR.getText());

    /**
     * 值
     */
    private String value;
    /**
     * 中文描述
     */
    private String text;


    ResultStatusEnum(String value, String text) {
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


}
