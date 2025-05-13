package edu.du.knjsystem_product.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BarcodeMixProductItemCertDto {
    private Long certId;
    private String stdCertNo;
    private String repItemName;
    private String frmrNm;
    private String itemName;
}
