package edu.du.knjsystem_product.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BarcodeInsertSingleDto {
    private Long f001Id;         // 대표상품 PK - 자동 생성
    private Long userNo;         // 회원번호
    private String barcodeNo;    // 상품바코드
    private String barcodeName;  // 상품명
    private String productType;  // "단일상품"
    private String registDate;   // CURRENT_DATE
}
