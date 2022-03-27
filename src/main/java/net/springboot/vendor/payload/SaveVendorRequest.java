package net.springboot.vendor.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.springboot.common.enums.Status;
import net.springboot.vendor.model.VendorContactInfo;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveVendorRequest implements Serializable {
    private long id;
    private String name;
    private List<VendorContactInfo> vendorContactInfos;
    private Status status;
}
