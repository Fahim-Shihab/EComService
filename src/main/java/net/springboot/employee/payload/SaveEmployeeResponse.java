package net.javaguides.springboot.employee.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.javaguides.springboot.common.base.ServiceResponse;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor

public class SaveEmployeeResponse extends ServiceResponse implements Serializable {

    public SaveEmployeeResponse(boolean b, String message) {
        super(b, message);
    }

}
