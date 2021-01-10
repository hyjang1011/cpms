/**
 * 
 */
package com.coupon.cpms.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.coupon.cpms.domain.UserInfoDTO;
import com.coupon.cpms.model.UserInfo;

/**
 * @author admin-PC
 *
 */

@Mapper
public interface UserInfoMapper {

	public Long insertUserInfoDTO(UserInfoDTO userDTO);

	public Long insertUserInfo(UserInfo userInfo);
	
	public UserInfo selectUserInfoByEmail(String email);
	//public Optional<UserInfo> selectUserInfoByEmail(String email);
	
	public int updateUserInfoByEmail(UserInfo userInfo);
	
	public Optional<UserInfo> selectUserInfoByOAuthIdAndProvider(String oauthId, String provider);
	
	public UserInfo selectUserInfoByOAuthId(String oauthId);
	
	public int updateUserInfoByOAuthId(UserInfo userInfo);
}
