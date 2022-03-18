package net.springboot.vendor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorContactInfo {
    private String contactPerson;
    private String contactEmail;
    private String contactPhone;
}
