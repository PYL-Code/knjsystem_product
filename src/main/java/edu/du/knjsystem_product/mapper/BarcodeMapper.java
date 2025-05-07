package edu.du.knjsystem_product.mapper;

import edu.du.knjsystem_product.dto.BarcodeDetailDto;
import edu.du.knjsystem_product.dto.BarcodeListDto;
import edu.du.knjsystem_product.dto.BarcodeSearchDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BarcodeMapper {
    List<BarcodeListDto> selectBarcodeList(@Param("search") BarcodeSearchDto search);

    BarcodeDetailDto selectBarcodeDetail(@Param("barcodeId") Long barcodeId);


}
