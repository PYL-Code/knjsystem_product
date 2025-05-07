package edu.du.knjsystem_product.service;

import edu.du.knjsystem_product.dto.MemberDto;
import edu.du.knjsystem_product.mapper.MemberMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberMapper memberMapper;

    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public MemberDto getMemberByNo(@Param("no") Long no) {
        return memberMapper.selectMemberByNo(no);
    }
}
