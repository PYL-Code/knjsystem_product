package edu.du.knjsystem_product.mapper;

import edu.du.knjsystem_product.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Mapper
public interface CertMapper {

    // 인증번호 리스트 조회 (리스트 페이지용)
    List<CertListDto> selectCertList(CertSearchDto certSearch);

    // 인증번호 상세 조회
    CertDetailDto selectCertDetail(@Param("seqNoA003") String seqNoA003);

    //바코드 상세조회 페이지에서 인증번호 조회
    BarcodeDetailCertInfoDto selectProductCertInfo(@Param("barcodeId") Long barcodeId);

    //바코드에 인증번호 추가 페이지에서 인증번호 리스트
    List<BarcodeAddCertListDto> selectBarcodeAddCertList(@Param("search") BarcodeAddCertSearchDto search);

}
