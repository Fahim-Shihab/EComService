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

    public String getValue() {
        return this.value;
    }

    public int getCode() {
        return this.code;
    }

}
