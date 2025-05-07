package edu.du.knjsystem_product.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    //T_SG_A001 회원업체정보 테이블
    private Long No; //회원업체정보 순번
    private String bnsNo; //사업자등록번호
    private String companyName; //업체명
    private String company880Code; //880업체코드
}
