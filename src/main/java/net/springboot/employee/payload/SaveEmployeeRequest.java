package net.springboot.employee.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveEmployeeRequest  implements Serializable {

    private long id;
    private String firstName;
    private String lastName;
    private String emailId;

}
