package edu.du.knjsystem_product.service;

import edu.du.knjsystem_product.dto.BarcodeDetailDto;
import edu.du.knjsystem_product.dto.BarcodeListDto;
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

    public List<BarcodeListDto> getBarcodesList(BarcodeSearchDto search) {
        return barcodeMapper.selectBarcodeList(search);
    }

    public BarcodeDetailDto getBarcode(Long barcodeId) {
        return barcodeMapper.selectBarcodeDetail(barcodeId);
    }

    public void updateBarcodeCert(Long certId, String barcodeId,  Long userNo) {
        barcodeMapper.updateCertForBarcode(certId, barcodeId, userNo);
    }
}
