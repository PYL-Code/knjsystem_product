package edu.du.knjsystem_product.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BarcodeAddCertListDto {
    private Long certId;           // A003.SEQ_NO_A003
    private Long memberCertId;     // A002.SEQ_NO_A002
    private String stdCertNo;      // 인증번호
    private String prdrGrpNm;      // 농가/단체명
    private String frmrNm;         // 소속농가
    private String repItemName;    // 대표품목
    private String itemName;       // 품목
}
