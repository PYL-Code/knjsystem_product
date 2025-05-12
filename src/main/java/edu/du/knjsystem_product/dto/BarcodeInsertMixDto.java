package edu.du.knjsystem_product.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BarcodeInsertMixDto {
    private Long g001Id;
    private Long g002Id;
    private Long userNo;
    private String barcodeNo;
    private String barcodeName;
    private String productType;          // "혼합상품"
    private String registDate;           // CURRENT_DATE
}
