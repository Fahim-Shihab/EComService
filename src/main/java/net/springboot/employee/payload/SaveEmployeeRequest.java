package net.springboot.employee.payload;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveEmployeeRequest  implements Serializable {

    private long id;
    private String firstName;
    private String lastName;
    private String emailId;

}
