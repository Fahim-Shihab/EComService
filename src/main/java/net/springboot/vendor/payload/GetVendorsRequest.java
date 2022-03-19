package net.springboot.vendor.payload;

import lombok.Getter;
import lombok.Setter;
import net.springboot.common.base.Page;
import net.springboot.common.enums.Status;

import java.io.Serializable;

@Getter
@Setter
public class GetVendorsRequest extends Page implements Serializable {
    private long id;
    private String name;
    Status status;
}
