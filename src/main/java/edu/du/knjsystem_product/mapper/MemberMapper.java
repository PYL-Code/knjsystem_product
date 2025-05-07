package edu.du.knjsystem_product.mapper;

import edu.du.knjsystem_product.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    MemberDto selectMemberByNo(@Param("no") Long no);
}