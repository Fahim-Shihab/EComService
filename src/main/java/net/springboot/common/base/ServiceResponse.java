package net.springboot.common.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ServiceResponse implements Serializable {
    private boolean status;
    private String message;

    public ServiceResponse() {
        this.status = true;
    }

    public ServiceResponse(boolean status) {
        this.status = status;
    }

    public ServiceResponse(String message) {
        this.status = false;
        this.message = message;
    }
}
