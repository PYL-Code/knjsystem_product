package edu.du.knjsystem_product.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CertListDto {
    private Long seqNoA002;
    private Long seqNoA003;
    private String certNo;
    private String repItemNm;
    private String groupName;
    private String certState;
    private String certType;
}
