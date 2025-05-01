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
    private String companyType; //업체구분
    private String repName; //대표이름
    private String repPhone; //대표전화
    private String repFax; //대표팩스
    private String address; //주소
    private String detailAddress; //상세주소
    private LocalDateTime regDate; //등록일
    private String ApprovalStatus; //승인구분
    private String deleteStatus; //삭제구분
}
