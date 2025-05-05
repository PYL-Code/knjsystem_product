package edu.du.knjsystem_product.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BarcodeSearchDto {
    private String barcodeNo;
    private String barcodeName;
    private String stdCertNo;
    private String repItemName;
    private String productType;
}
