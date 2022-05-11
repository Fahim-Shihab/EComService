package net.springboot.common.enums;

import java.io.Serializable;

public enum Status implements Serializable {
    Active("A", "Active"),
    Inactive("I","Inactive");

    private final String code;
    private final String value;

    Status(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static Status getByCode(String id) {
        for (Status e : values()) {
            if (e.code.equals(id)) {
                return e;
            }
        }
        return null;
    }
    public static Status getByValue(String displayName) {
        for (Status e : values()) {
            if (e.value.equals(displayName)) {
                return e;
            }
        }
        return null;
    }

    public String getValue() {
        return this.value;
    }

    public String getCode() {
        return this.code;
    }
}
