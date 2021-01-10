/**
 * 
 */
package com.coupon.cpms.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin-PC
 *
 */

@Getter
@Setter
public class UserInfoDTO {
	
	private Long userUno;
	private String email;
	private String passwrd;
	private String auth;
	private String accessToken;
	private String oauthId;
	private String providerType;
	private String nickname;
	
	/** 삭제 여부 */
	private String deleteYn;

	/** 등록일 */
	private LocalDateTime registDt;

	/** 수정일 */
	private LocalDateTime updateDt;

	/** 삭제일 */
	private LocalDateTime deleteDt;
}
