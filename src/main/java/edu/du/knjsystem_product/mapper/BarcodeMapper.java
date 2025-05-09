package edu.du.knjsystem_product.mapper;

import edu.du.knjsystem_product.dto.BarcodeDetailDto;
import edu.du.knjsystem_product.dto.BarcodeListDto;
import edu.du.knjsystem_product.dto.BarcodeSearchDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BarcodeMapper {
    List<BarcodeListDto> selectBarcodeList(@Param("search") BarcodeSearchDto search);

    BarcodeDetailDto selectBarcodeDetail(@Param("barcodeId") Long barcodeId);

    //바코드에 인증정보 추가
    @Update("""
    UPDATE T_SG_A004
    SET SEQ_NO_A003 = #{certId}
    WHERE SEQ_NO_A004 = #{barcodeId}
      AND SEQ_NO_A001 = #{userNo}
    """)
    int updateCertForBarcode(@Param("certId") Long certId,
                             @Param("barcodeId") Long barcodeId,
                             @Param("userNo") Long userNo);

    //바코드에 연결된 인증정보 삭제
    @Update("UPDATE T_SG_A004 SET SEQ_NO_A003 = NULL WHERE SEQ_NO_A004 = #{barcodeId}")
    void detachCertFromBarcode(@Param("barcodeId") Long barcodeId);


    //바코드 삭제 로직
    @Select("SELECT COUNT(*) FROM T_SG_F001 WHERE PRDR_BAR_CD = #{barcode} AND DEL_YN = 'N' AND PRDR_BAR_CD IN (SELECT PRDR_BAR_CD FROM T_SG_A004 WHERE SEQ_NO_A003 IS NULL)")
    boolean existsInF001WithoutCert(@Param("barcode") String barcode);

    @Select("SELECT COUNT(*) FROM T_SG_G001 WHERE PRDR_BAR_CD = #{barcode} AND DEL_YN = 'N' AND PRDR_BAR_CD IN (SELECT PRDR_BAR_CD FROM T_SG_A004 WHERE SEQ_NO_A003 IS NULL)")
    boolean existsInG001WithoutCert(@Param("barcode") String barcode);

    @Delete("DELETE FROM T_SG_A004 WHERE PRDR_BAR_CD = #{barcode} AND SEQ_NO_A003 IS NULL")
    void deleteFromA004(@Param("barcode") String barcode);

    @Delete("DELETE FROM T_SG_F001 WHERE PRDR_BAR_CD = #{barcode}")
    void deleteFromF001(@Param("barcode") String barcode);

    @Delete("DELETE FROM T_SG_G001 WHERE PRDR_BAR_CD = #{barcode}")
    void deleteFromG001(@Param("barcode") String barcode);
}
