package edu.du.knjsystem_product.controller;

import edu.du.knjsystem_product.dto.BarcodeDetailDto;
import edu.du.knjsystem_product.dto.BarcodeListDto;
import edu.du.knjsystem_product.dto.BarcodeSearchDto;
import edu.du.knjsystem_product.security.CustomUserDetails;
import edu.du.knjsystem_product.service.BarcodeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/barcode")
public class BarcodeRestController {

    private final BarcodeService barcodeService;

    public BarcodeRestController(BarcodeService barcodeService) {
        this.barcodeService = barcodeService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<BarcodeListDto>> getBarcodeList(@ModelAttribute BarcodeSearchDto search) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        search.setSeqNoA001(user.getSeqNoA001());

        List<BarcodeListDto> list = barcodeService.getBarcodesList(search);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> registerBarcode(@RequestBody Map<String, Object> payload) {
        barcodeService.processBarcodeRegistration(payload);
        return ResponseEntity.ok("바코드 등록 성공");
    }

    @GetMapping("/detail")
    public ResponseEntity<BarcodeDetailDto> getBarcodeDetail(@Param("barcodeId") Long barcodeId) {
//        System.out.println("✅ 조회 요청 받은 barcodeId: " + barcodeId);

        BarcodeDetailDto barcode = barcodeService.getBarcode(barcodeId);
        return ResponseEntity.ok(barcode);
    }

    @PostMapping("/update/cert/into/barcode")
    public ResponseEntity<String> updateCertInfo(@Param("certId") Long certId,
                                                 @Param("barcodeId") Long barcodeId,
                                                 @Param("userNo") Long userNo) {
        barcodeService.updateBarcodeCert(certId, barcodeId, userNo);
        return ResponseEntity.ok("인증정보 등록 완료되었습니다.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBarcode(@Param("barcode") String barcode) {
        barcodeService.deleteBarcodeAndProduct(barcode);
        return ResponseEntity.ok("바코드 삭제 완료되었습니다.");
    }

    @PostMapping("/delete/cert/of/barcode")
    public ResponseEntity<String> deleteBarcodeCert(@Param("barcodeId") Long barcodeId,
                                                    @Param("barcodeNo") String barcodeNo,
                                                    @Param("productType") String productType) {
        barcodeService.deleteBarcodeCert(barcodeId, barcodeNo, productType);
        return ResponseEntity.ok("인증정보 삭제 완료되었습니다.");
    }

}
