package edu.du.knjsystem_product;

import edu.du.knjsystem_product.dto.BarcodeDto;
import edu.du.knjsystem_product.dto.BarcodeSearchDto;
import edu.du.knjsystem_product.mapper.BarcodeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class KnjsystemProductApplicationTests {

    @Autowired
    private BarcodeMapper barcodeMapper;

    @Test
    void contextLoads() {
        BarcodeSearchDto search = new BarcodeSearchDto();
        System.out.println(barcodeMapper.selectBarcodeList(search));
    }

    @Test
    void testSelect() {
        BarcodeSearchDto search = new BarcodeSearchDto();
        search.setBarcodeNo("8800001000011");
        List<BarcodeDto> result = barcodeMapper.selectBarcodeList(search);
        result.forEach(System.out::println);
    }

}
