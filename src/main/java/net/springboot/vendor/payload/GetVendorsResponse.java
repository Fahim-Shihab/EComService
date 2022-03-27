package net.springboot.vendor.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.springboot.common.base.ServiceResponse;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetVendorsResponse extends ServiceResponse implements Serializable {
    private List<VendorDto> vendorList;

    public GetVendorsResponse(){
        vendorList = new ArrayList<>();
    }

    public GetVendorsResponse(String message) {
        super(false, message);
    }

    BigInteger total;
}
