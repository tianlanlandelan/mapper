package com.keller.commonutil.enums;

/**
 * 字符串枚举
 * @author yangkaile
 * @date 2021-02-07 13:24:33
 */
public enum StringEnums {
    Split(",","分隔符，英文逗号");

    public String value;

    public String name;

    StringEnums(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
