package edu.du.knjsystem_product.mapper;

import edu.du.knjsystem_product.dto.BarcodeDto;
import edu.du.knjsystem_product.dto.BarcodeSearchDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BarcodeMapper {
    List<BarcodeDto> selectBarcodeList(@Param("search") BarcodeSearchDto search);
}
