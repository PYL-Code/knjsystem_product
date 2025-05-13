package edu.du.knjsystem_product.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BarcodeMixProductItemInsertDto {
    private Long g001Id;           // 혼합상품 PK
    private String itemName;       // 구성상품명
    private Long g002Id;           // 구성상품 PK (자동 생성된 값)
}
