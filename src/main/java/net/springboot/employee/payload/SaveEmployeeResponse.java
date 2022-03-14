package net.springboot.employee.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.springboot.common.base.ServiceResponse;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor

public class SaveEmployeeResponse extends ServiceResponse implements Serializable {

    public SaveEmployeeResponse(boolean b, String message) {
        super(b, message);
    }

}
