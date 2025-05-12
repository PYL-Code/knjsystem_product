package edu.du.knjsystem_product.mapper;

import edu.du.knjsystem_product.dto.*;
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

    @Delete("DELETE FROM T_SG_F002 WHERE SEQ_NO_F001 = (SELECT SEQ_NO_F001 FROM T_SG_F001 WHERE PRDR_BAR_CD = #{barcodeNo})")
    void deleteCertFromF002(@Param("barcodeNo") String barcodeNo);

    @Delete("""
    DELETE FROM T_SG_G003
    WHERE SEQ_NO_G002 IN (
      SELECT G2.SEQ_NO_G002
      FROM T_SG_G002 G2
      JOIN T_SG_G001 G1 ON G1.SEQ_NO_G001 = G2.SEQ_NO_G001
      WHERE G1.PRDR_BAR_CD = #{barcodeNo}
    )
    """)
    void deleteCertFromG003(@Param("barcodeNo") String barcodeNo);


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



    /* 바코드 등록 단일상품 */
    @Insert("INSERT INTO T_SG_F001 (SEQ_NO_A001, PRDR_BAR_CD, PRDR_BAR_NM, CERT_STATE, REGIST_DATE, DEL_YN) " +
            "VALUES (#{userNo}, #{barcodeNo}, #{barcodeName}, '정상', CURRENT_DATE, 'N')")
    @Options(useGeneratedKeys = true, keyProperty = "f001Id", keyColumn = "SEQ_NO_F001")
    void insertF001(BarcodeInsertSingleDto dto);

    @Insert("INSERT INTO T_SG_F002 (SEQ_NO_F001, REGIST_DATE, DEL_YN) " +
            "VALUES (#{f001Id}, CURRENT_DATE, 'N')")
    void insertF002(BarcodeInsertSingleDto dto);

    @Insert("INSERT INTO T_SG_A004 (SEQ_NO_A001, PRDR_BAR_CD, PRDR_BAR_NM, STATUS) " +
            "VALUES (#{userNo}, #{barcodeNo}, #{barcodeName}, 'I')")
    void insertA004(BarcodeInsertSingleDto dto);


    /* 바코드 등록 혼합상품 */
    @Insert("INSERT INTO T_SG_G001 (SEQ_NO_A001, PRDR_BAR_CD, PRDR_BAR_NM, CERT_STATE, REGIST_DATE, DEL_YN) " +
            "VALUES (#{userNo}, #{barcodeNo}, #{barcodeName}, '정상', CURRENT_DATE, 'N')")
    @Options(useGeneratedKeys = true, keyProperty = "g001Id", keyColumn = "SEQ_NO_G001")
    void insertG001(BarcodeInsertMixDto dto);

    @Insert("INSERT INTO T_SG_G002 (SEQ_NO_G001, REGIST_DATE, DEL_YN) " +
            "VALUES (#{g001Id}, CURRENT_DATE, 'N')")
    @Options(useGeneratedKeys = true, keyProperty = "g002Id", keyColumn = "SEQ_NO_G002")
    void insertG002(BarcodeInsertMixDto dto);

    @Insert("INSERT INTO T_SG_G003 (SEQ_NO_G002, REGIST_DATE, DEL_YN) " +
            "VALUES (#{g002Id}, CURRENT_DATE, 'N')")
    void insertG003(BarcodeInsertMixDto dto);

    @Insert("INSERT INTO T_SG_A004 (SEQ_NO_A001, PRDR_BAR_CD, PRDR_BAR_NM, STATUS) " +
            "VALUES (#{userNo}, #{barcodeNo}, #{barcodeName}, 'I')")
    void insertA004ForMix(BarcodeInsertMixDto dto);
}
