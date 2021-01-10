/**
 * 
 */
package com.coupon.cpms.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.coupon.cpms.configuration.jwt.JwtTokenProvider;
import com.coupon.cpms.service.ResponseService;

/**
 * @author admin-PC
 *
 */
public class ParentController {

	@Autowired
	protected ResponseService responseService;
	
	@Autowired	
	protected JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
}
