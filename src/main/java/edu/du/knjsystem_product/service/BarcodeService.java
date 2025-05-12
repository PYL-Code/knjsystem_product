package edu.du.knjsystem_product.service;

import edu.du.knjsystem_product.dto.*;
import edu.du.knjsystem_product.mapper.BarcodeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

    public void updateBarcodeCert(Long certId, Long barcodeId,  Long userNo) {
        barcodeMapper.updateCertForBarcode(certId, barcodeId, userNo);
    }

    @Transactional
    public void deleteBarcodeCert(Long barcodeId, String barcodeNo, String productType) {
        barcodeMapper.detachCertFromBarcode(barcodeId);

        if ("단일상품".equals(productType)) {
            barcodeMapper.deleteCertFromF002(barcodeNo);
        } else if ("혼합상품".equals(productType)) {
            barcodeMapper.deleteCertFromG003(barcodeNo);
        }
    }


    @Transactional
    public void deleteBarcodeAndProduct(String barcode) {
        if (barcodeMapper.existsInF001WithoutCert(barcode)) {
            barcodeMapper.deleteFromA004(barcode);
            barcodeMapper.deleteFromF001(barcode);
        } else if (barcodeMapper.existsInG001WithoutCert(barcode)) {
            barcodeMapper.deleteFromA004(barcode);
            barcodeMapper.deleteFromG001(barcode);
        }
    }

    //바코드 추가 혼합상품 단일상품 구분
    @Transactional
    public void processBarcodeRegistration(Map<String, Object> payload) {
        String productType = (String) payload.get("productType");

        if ("단일상품".equals(productType)) {
            BarcodeInsertSingleDto dto = BarcodeInsertSingleDto.builder()
                    .userNo(Long.valueOf(payload.get("userNo").toString()))
                    .barcodeNo((String) payload.get("barcodeNo"))
                    .barcodeName((String) payload.get("barcodeName"))
                    .productType("단일상품")
                    .build();

            registerSingleProduct(dto);

        } else if ("혼합상품".equals(productType)) {
            BarcodeInsertMixDto dto = BarcodeInsertMixDto.builder()
                    .userNo(Long.valueOf(payload.get("userNo").toString()))
                    .barcodeNo((String) payload.get("barcodeNo"))
                    .barcodeName((String) payload.get("barcodeName"))
                    .productType("혼합상품")
                    .build();

            registerMixedProduct(dto);
        }
    }

    //바코드 추가 단일상품
    @Transactional
    public void registerSingleProduct(BarcodeInsertSingleDto dto) {
        barcodeMapper.insertF001(dto);
        barcodeMapper.insertF002(dto);
        barcodeMapper.insertA004(dto);
    }

    //바코드 추가 혼합상품
    @Transactional
    public void registerMixedProduct(BarcodeInsertMixDto dto) {
        barcodeMapper.insertG001(dto);
        barcodeMapper.insertG002(dto);
        barcodeMapper.insertG003(dto);
        barcodeMapper.insertA004ForMix(dto);
    }
}
