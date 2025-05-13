package edu.du.knjsystem_product.controller;

import edu.du.knjsystem_product.dto.*;
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

    @PostMapping("/mix/product/add/item")
    public ResponseEntity<String> mixProductAddItem(@RequestBody BarcodeMixProductItemInsertDto productItem) {
        barcodeService.mixProductAddItem(productItem);
        return ResponseEntity.ok("구성상품이 추가되었습니다.");
    }

    //바코드 혼합상품 인증번호 등록
    @PostMapping("/mix/product/insert/cert")
    public ResponseEntity<String> insertBarcodeMixProductCert(@RequestParam("g002Id") Long itemId,
                                                              @RequestParam("certId") Long certId) {
        barcodeService.registerG003Cert(itemId, certId);
        return ResponseEntity.ok("인증정보가 구성상품에 등록되었습니다.");
    }

    //바코드 혼합상품 인증번호 삭제
    @DeleteMapping("/mix/product/delete/cert")
    public ResponseEntity<String> deleteBarcodeMixProductCert(@RequestParam("g002Id") Long g002Id,
                                                              @RequestParam("certId") Long certId) {
        barcodeService.deleteG003Cert(g002Id, certId);
        return ResponseEntity.ok("구성상품의 인증정보가 삭제되었습니다.");
    }


    @DeleteMapping("/mix/product/delete")
    public ResponseEntity<String> deleteBarcodeMixProduct(@RequestParam("g002Id") Long g002Id) {
        barcodeService.deleteG002Item(g002Id);
        return ResponseEntity.ok("구성상품 삭제 완료");
    }

    @GetMapping("/mix/product/list")
    public ResponseEntity<List<BarcodeMixProductItemDto>> getMixProductList(@RequestParam("barcodeId") Long g001Id) {
        List<BarcodeMixProductItemDto> list = barcodeService.getG002ItemList(g001Id);
        return ResponseEntity.ok(list);
    }
}
