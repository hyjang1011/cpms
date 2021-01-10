/**
 * 
 */
package com.coupon.cpms.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author admin-PC
 *
 */

@Builder
@Setter
@Getter
@NoArgsConstructor // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
public class UserInfo implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4258584557105559112L;
	private Long userUno;
	private String email;
	private String passwrd;
	private String auth;
	private String accessToken;
	private String oauthId;
	private String providerType;
	private String nickname;

	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private List<String> roles = new ArrayList<>();
	
	/**
	 *	사용자의 권한을 콜렉션 형태로 반환
	 *	단, 클래스 자료형은 GrantedAuthority를 구현해야함
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> roles = new HashSet<>();
		
	    for (String role : auth.split(",")) {
	      roles.add(new SimpleGrantedAuthority(role));
	    }
	    
	    return roles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return passwrd;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	
	/**
	 *	계정 만료 여부
	 */
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		//return false;	//만료됨
		return true;	//만료되지 않음
	}

	/**
	 *	계정 잠금 여부
	 */
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		//return false;	//잠김
		return true;	//잠기지 않음
	}

	/**
	 *	비밀번호 만료 여부
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		//return false;	//만료됨
		return true;	//만료 안됨
	}

	/**
	 *	계정 사용 가능 여부
	 */
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		//return false;	//사용 불가함
		return true;	//사용 가능함
	}

	public UserInfo orElseThrow(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

}
