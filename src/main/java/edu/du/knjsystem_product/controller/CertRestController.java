package edu.du.knjsystem_product.controller;

import edu.du.knjsystem_product.dto.BarcodeAddCertListDto;
import edu.du.knjsystem_product.dto.BarcodeAddCertSearchDto;
import edu.du.knjsystem_product.dto.BarcodeDetailCertInfoDto;
import edu.du.knjsystem_product.service.CertService;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cert")
public class CertRestController {

    private final CertService certService;

    public CertRestController(CertService certService) {
        this.certService = certService;
    }

    @GetMapping("barcode/detail")
    public ResponseEntity<BarcodeDetailCertInfoDto> getBarcodeDetail(@Param("seqNoA004") Long seqNoA004) {
        BarcodeDetailCertInfoDto certInfo = certService.selectProductCertInfo(seqNoA004);
        return ResponseEntity.ok(certInfo);
    }

    @GetMapping("/barcode/update")
    public ResponseEntity<List<BarcodeAddCertListDto>> getBarcodeUpdateCertList(@ModelAttribute BarcodeAddCertSearchDto search) {
        List<BarcodeAddCertListDto> list = certService.selectBarcodeAddCertList(search);
        return ResponseEntity.ok(list);
    }
}
