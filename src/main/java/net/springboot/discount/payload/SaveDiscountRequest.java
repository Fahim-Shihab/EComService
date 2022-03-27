package net.springboot.discount.payload;

import lombok.Getter;
import lombok.Setter;
import net.springboot.common.enums.Status;

import java.io.Serializable;

@Getter
@Setter
public class SaveDiscountRequest implements Serializable {
    String id;
    String code;
    double percentage_value;
    String activeFrom;
    String activeTo;
    Status status;
}
