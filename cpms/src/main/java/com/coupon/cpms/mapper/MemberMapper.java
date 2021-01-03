/**
 * 
 */
package com.coupon.cpms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.coupon.cpms.domain.MemberDTO;

/**
 * @author admin-PC
 *
 */
@Mapper
public interface MemberMapper {

	public int insertMember(MemberDTO params);
	
	public MemberDTO selectMemberByOAuthId(String oauthId);
	
	public int updateMemberByOAuthId(MemberDTO params);
}
