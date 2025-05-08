package edu.du.knjsystem_product.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BarcodeListDto {
    private Long barcodeId;         // SeqNoA004
    private String barcodeNo;        // 바코드번호
    private String barcodeName;      // 상품바코드명
    private LocalDateTime barcodeRegDate;// 바코드 등록일
    private String productType;      // 단일상품 or 혼합상품
    private String stdCertNo;        // 인증번호
    private String repItemName;      // 대표품목명
}
