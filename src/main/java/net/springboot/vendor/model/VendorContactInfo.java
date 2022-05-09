package net.springboot.vendor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorContactInfo implements Serializable {
    private String contactPerson;
    private String contactEmail;
    private String contactPhone;
}
