package edu.du.knjsystem_product.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertSearchDto {
    private int memberId;
    private String itemCd;
    private String groupName;
    private String farmerName;
    private String certState;
    private String certNo;
    private String itemNm;
    private String hasBarcode; // "Y" or "N"
}
