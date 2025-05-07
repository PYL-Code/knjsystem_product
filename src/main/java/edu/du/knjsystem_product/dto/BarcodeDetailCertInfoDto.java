package edu.du.knjsystem_product.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BarcodeDetailCertInfoDto {
    private Long certId;          // PK: 인증정보 순번 (SEQ_NO_A003)
    private String stdCertNo;     // 인증번호
    private String certGbNm;      // 인증구분명
    private String certStartDate; // 인증시작일
    private String certEndDate;   // 인증종료일
    private String certStateNm;   // 인증상태명
    private String prdrGrpNm;     // 생산자단체명
    private String psnGroupNm;    // 개인/단체 구분명
    private String repItemNm;     // 대표품목명
    private String itemNm;        // 품목명
    private String frmrNm;        // 소속농가명
}
