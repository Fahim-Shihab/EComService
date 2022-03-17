package net.springboot.common.enums;

import java.io.Serializable;

public enum UserRole implements Serializable {

    Admin("A", "Admin"),
    Employee("E","Employee"),
    Customer("C","Customer");

    private final String code;
    private final String value;

    UserRole(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String getCode() {
        return this.code;
    }
}
