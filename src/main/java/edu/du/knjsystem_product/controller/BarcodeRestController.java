package edu.du.knjsystem_product.controller;

import edu.du.knjsystem_product.dto.BarcodeDto;
import edu.du.knjsystem_product.dto.BarcodeSearchDto;
import edu.du.knjsystem_product.service.BarcodeService;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/getlist")
    public ResponseEntity<List<BarcodeDto>> getBarcodeList(@ModelAttribute BarcodeSearchDto search) {
        List<BarcodeDto> list = barcodeService.getBarcodes(search);
        return ResponseEntity.ok(list);
    }
}
