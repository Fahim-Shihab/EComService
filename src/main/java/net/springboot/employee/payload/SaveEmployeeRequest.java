package net.springboot.employee.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.springboot.common.enums.Status;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveEmployeeRequest  implements Serializable {

    private long id;
    private Date dob;
    private long userId;
    private Status status;

}
