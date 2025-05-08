package edu.du.knjsystem_product.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BarcodeAddCertSearchDto {
    private Long userNo;          // 회원업체정보 PK (SEQ_NO_A001)
    private String stdCertNo;     // 인증번호 검색어
    private String repItemName;   // 대표품목명
    private String itemName;      // 품목명
    private String all;
}
