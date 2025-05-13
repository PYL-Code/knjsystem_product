package edu.du.knjsystem_product.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BarcodeDetailDto {
    private Long barcodeId;
    private String barcode;
    private String barcodeName;
    private String productType;
    private Long g001Id;
}
