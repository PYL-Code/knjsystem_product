package edu.du.knjsystem_product.service;

import edu.du.knjsystem_product.dto.CertDetailDto;
import edu.du.knjsystem_product.dto.CertListDto;
import edu.du.knjsystem_product.dto.CertSearchDto;
import edu.du.knjsystem_product.mapper.CertMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertService {

    private final CertMapper certMapper;

    public CertService(CertMapper certMapper) {
        this.certMapper = certMapper;
    }

    public List<CertListDto> selectCertList(CertSearchDto certSearch) {
        return certMapper.selectCertList(certSearch);
    }

    public CertDetailDto selectCertDetail(@Param("certNo") String certNo) {
        return certMapper.selectCertDetail(certNo);
    }
}
