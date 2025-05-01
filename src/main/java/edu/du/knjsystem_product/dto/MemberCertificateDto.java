package edu.du.knjsystem_product.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberCertificateDto {
    //회원보유인증번호 테이블
    private Long memCertNo; //회원보유인증번호 순번
    private Long MemberNo; //회원업체정보 순번
    private String StdCertNo; //인증번호

    //T_SG_A003 친환경농산물인증정보 테이블
    private Long a003No; //순번
    private String rcv; //인증신청일련번호
    private String mod; //변경일련번호
    private String producerGroupNo; //생산자단체번호
    private String reg; //등록일련번호
//    private String StdCertNo; //인증번호
    private String certGbCode; //인증구분코드
    private String certGbName; //인증구분명
    private String certStartDate; //인증시작일자
    private String certEndDate; //인증종료일자
    private String frmrCertState; //인증상태코드
    private String certStateName; //인증상태명
    private String personalGroupGb; //개인단체구분
    private String personalGroupName; //개인단체구분명
    private String ProducerGroupNo; //생산자단체명
    private String repItemCode; //대표품목코드
    private String repItemName; //대표품목명
    private String farmerNo; //소속농가번호
    private String farmerName; //소속농가명
    private String itemCode; //품목코드
    private String itemName; //품목명
    private LocalDateTime regDate; //등록일
    private String status; //등록상태
}
