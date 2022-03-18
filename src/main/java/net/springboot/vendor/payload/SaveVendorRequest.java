package net.springboot.vendor.payload;

import lombok.*;
import net.springboot.common.enums.Status;
import net.springboot.vendor.model.VendorContactInfo;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveVendorRequest {
    private String id;
    private String name;
    private List<VendorContactInfo> vendorContactInfos;
    private Status status;
}
