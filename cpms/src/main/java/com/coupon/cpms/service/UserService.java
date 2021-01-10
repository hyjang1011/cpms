/**
 * 
 */
package com.coupon.cpms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.coupon.cpms.domain.UserInfoDTO;
import com.coupon.cpms.mapper.BoardMapper;
import com.coupon.cpms.mapper.UserInfoMapper;
import com.coupon.cpms.model.UserInfo;

import lombok.RequiredArgsConstructor;

/**
 * @author admin-PC
 *
 */

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserInfoMapper userInfoMapper;
	
	/**
	 * Spring Security 필수 메소드 구현
	 * 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정
	 * 
	 * @param email 이메일
	 * @return UserDetails
	 * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
	 *
	 */
	@Override
	public UserInfo loadUserByUsername(String email) {
		/*
		 * return userRepository.findByEmail(email) .orElseThrow(() -> new
		 * UsernameNotFoundException((email)));
		 */
		UserInfo userInfo = userInfoMapper.selectUserInfoByEmail(email);
		
		if(userInfo != null) {
			return userInfo;
		} else {
			throw new UsernameNotFoundException(email);
		}
	}
	
	/**
	 * 회원정보 저장
	 * 2021.01.10 정리후 제거할 것
	 * 
	 * @param userInfoDTO
	 * @return
	 */
	public Long saveUserInfoDTO(UserInfoDTO userInfoDTO) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		userInfoDTO.setPasswrd(encoder.encode(userInfoDTO.getPasswrd()));
		
		return userInfoMapper.insertUserInfoDTO(userInfoDTO);
	}
	
	/**
	 * 회원정보 저장
	 * 
	 * @param userInfoDTO
	 * @return
	 */
	public Long saveUserInfo(UserInfo userInfo) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		userInfo.setPasswrd(encoder.encode(userInfo.getPasswrd()));
		
		return userInfoMapper.insertUserInfo(userInfo);
	}
	

	/**
	 * @param email
	 * @return
	 */
	public UserInfo findByUid(String email) {
		return userInfoMapper.selectUserInfoByEmail(email);
	}
	
	public Optional<UserInfo> findByOAuthIdAndProvider(String oauthId, String provider) {
		return userInfoMapper.selectUserInfoByOAuthIdAndProvider(oauthId, provider);
	}

}
