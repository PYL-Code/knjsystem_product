package edu.du.knjsystem_product.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertDetailDto {
    private Long seqNoA002;
    private Long seqNoA003;
    private String certNo;
    private String certType;
    private String certStartDate;
    private String certEndDate;
    private String certState;
    private String certOrg;
    private String groupName;
    private String groupType;
    private String repItemName;
    private String certRenewalStatus;
}
