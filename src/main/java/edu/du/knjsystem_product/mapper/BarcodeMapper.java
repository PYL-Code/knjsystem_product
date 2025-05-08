package edu.du.knjsystem_product.mapper;

import edu.du.knjsystem_product.dto.BarcodeDetailDto;
import edu.du.knjsystem_product.dto.BarcodeListDto;
import edu.du.knjsystem_product.dto.BarcodeSearchDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BarcodeMapper {
    List<BarcodeListDto> selectBarcodeList(@Param("search") BarcodeSearchDto search);

    BarcodeDetailDto selectBarcodeDetail(@Param("barcodeId") Long barcodeId);

    //바코드에 인증정보 추가
    @Update("""
    UPDATE T_SG_A004
    SET SEQ_NO_A003 = #{certId}
    WHERE PRDR_BAR_CD = #{barcodeNo}
      AND SEQ_NO_A001 = #{userNo}
""")
    int updateCertForBarcode(@Param("certId") Long certId,
                             @Param("barcodeNo") String barcodeNo,
                             @Param("userNo") Long userNo);

}
