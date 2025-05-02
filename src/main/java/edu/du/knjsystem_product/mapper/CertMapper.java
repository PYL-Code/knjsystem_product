package edu.du.knjsystem_product.mapper;

import edu.du.knjsystem_product.dto.CertDetailDto;
import edu.du.knjsystem_product.dto.CertListDto;
import edu.du.knjsystem_product.dto.CertSearchDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CertMapper {

    // 인증번호 리스트 조회 (리스트 페이지용)
    List<CertListDto> selectCertList(CertSearchDto certSearch);

    // 인증번호 상세 조회
    CertDetailDto selectCertDetail(@Param("seqNoA003") String seqNoA003);
}
