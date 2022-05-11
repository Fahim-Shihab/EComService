package net.springboot.common.enums;

import java.io.Serializable;

public enum OrderStatus implements Serializable {

    Ordered(0, "Ordered"),
    Confirmed(1,"Confirmed"),
    OnTheWay(2,"OnTheWay"),
    Delivered(3,"Delivered"),
    Returned(4,"Returned"),
    ;

    private final int code;
    private final String value;

    OrderStatus(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static OrderStatus getByCode(int id) {
        for (OrderStatus e : values()) {
            if (e.code == id) {
                return e;
            }
        }
        return null;
    }
    public static OrderStatus getByValue(String displayName) {
        for (OrderStatus e : values()) {
            if (e.value.equals(displayName)) {
                return e;
            }
        }
        return null;
    }

    public String getValue() {
        return this.value;
    }

    public int getCode() {
        return this.code;
    }

}
