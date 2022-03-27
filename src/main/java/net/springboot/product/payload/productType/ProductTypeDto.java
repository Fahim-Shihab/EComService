package net.springboot.product.payload.productType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductTypeDto {
    private long id;
    private String category;
    private String subCategory;
    private String status;
}
