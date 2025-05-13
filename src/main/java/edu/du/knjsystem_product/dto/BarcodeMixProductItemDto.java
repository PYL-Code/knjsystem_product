package edu.du.knjsystem_product.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BarcodeMixProductItemDto {
    private Long g002Id;
    private String itemName;
    private List<BarcodeMixProductItemCertDto> certs;
}
