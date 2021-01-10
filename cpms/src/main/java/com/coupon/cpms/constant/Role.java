/**
 * 
 */
package com.coupon.cpms.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author admin-PC
 *
 */

@AllArgsConstructor
@Getter
public enum Role {
	
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "일반 사용자");

    private String key;
    private String title;
}
