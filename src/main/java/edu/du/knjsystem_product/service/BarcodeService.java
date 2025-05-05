package edu.du.knjsystem_product.service;

import edu.du.knjsystem_product.dto.BarcodeDto;
import edu.du.knjsystem_product.dto.BarcodeSearchDto;
import edu.du.knjsystem_product.mapper.BarcodeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarcodeService {

    private final BarcodeMapper barcodeMapper;

    public BarcodeService(BarcodeMapper barcodeMapper) {
        this.barcodeMapper = barcodeMapper;
    }

    public List<BarcodeDto> getBarcodes(BarcodeSearchDto search) {
        return barcodeMapper.selectBarcodeList(search);
    }
}
