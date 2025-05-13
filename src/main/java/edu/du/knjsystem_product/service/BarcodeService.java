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

    //혼합상품 디테일 구성상품 추가
    public void mixProductAddItem(BarcodeMixProductItemInsertDto item) {
        barcodeMapper.insertG002Item(item);
    }

    //혼합상품 디테일 구성상품에 인증번호 추가
    public void registerG003Cert(Long g002Id, Long certId) {
        barcodeMapper.insertG003Cert(g002Id, certId);
    }

    //혼합상품 구성상품 인증번호 삭제
    public void deleteG003Cert(Long g002Id, Long certId) {
        barcodeMapper.deleteG003Cert(g002Id, certId);
    }

    //혼합상품 구성상품 삭제
    public void deleteG002Item(Long g002Id) {
        barcodeMapper.deleteG002Only(g002Id);
    }

    //혼합상품 구성상품 리스트 불러오기
    public List<BarcodeMixProductItemDto> getG002ItemList(Long g001Id) {
        return barcodeMapper.selectG002ItemList(g001Id);
    }
}
