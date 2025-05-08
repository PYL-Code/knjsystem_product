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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/detail")
    public ResponseEntity<BarcodeDetailDto> getBarcodeDetail(@Param("barcodeId") Long barcodeId) {
//        System.out.println("✅ 조회 요청 받은 barcodeId: " + barcodeId);

        BarcodeDetailDto barcode = barcodeService.getBarcode(barcodeId);
        return ResponseEntity.ok(barcode);
    }
}
