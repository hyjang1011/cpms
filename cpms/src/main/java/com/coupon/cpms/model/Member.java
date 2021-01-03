/**
 * 
 */
package com.coupon.cpms.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author admin-PC
 *
 */

@Getter
@Setter
public class Member {

	private Long id;
	private String providerUserId;
	private String email;
	private String password;
	private String provider;
	
}
