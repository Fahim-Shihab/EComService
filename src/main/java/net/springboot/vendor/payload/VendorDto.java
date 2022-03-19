package net.springboot.vendor.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.springboot.vendor.model.VendorContactInfo;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VendorDto {
    private long id;
    private String name;
    private List<VendorContactInfo> vendorContactInfos;
    private String status;
}
