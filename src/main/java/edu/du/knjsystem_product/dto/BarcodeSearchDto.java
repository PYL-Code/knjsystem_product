package edu.du.knjsystem_product.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BarcodeSearchDto {
    private Long seqNoA001;
    private String barcodeNo;
    private String barcodeName;
    private String stdCertNo;
    private String repItemName;
    private String productType;
    private String all;
}
