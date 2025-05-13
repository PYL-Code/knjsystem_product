package edu.du.knjsystem_product.controller;

import edu.du.knjsystem_product.dto.BarcodeAddCertListDto;
import edu.du.knjsystem_product.dto.BarcodeAddCertSearchDto;
import edu.du.knjsystem_product.dto.BarcodeDetailCertInfoDto;
import edu.du.knjsystem_product.service.BarcodeService;
import edu.du.knjsystem_product.service.CertService;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cert")
public class CertRestController {

    private final CertService certService;
    private final BarcodeService barcodeService;

    public CertRestController(CertService certService, BarcodeService barcodeService) {
        this.certService = certService;
        this.barcodeService = barcodeService;
    }

    @GetMapping("/barcode/detail")
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
