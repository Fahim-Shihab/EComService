package net.springboot.product.payload;

import lombok.Getter;
import lombok.Setter;
import net.springboot.common.enums.Status;

import java.io.Serializable;

@Getter
@Setter

public class SaveProductTypeRequest implements Serializable {
    private long id;
    private String category;
    private String subCategory;
    private Status status;
}
