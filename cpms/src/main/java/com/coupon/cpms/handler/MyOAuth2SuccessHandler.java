/**
 * 
 */
package com.coupon.cpms.handler;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


/**
 * @author admin-PC
 *
 */
public class MyOAuth2SuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
        String id = authentication.getName();

        LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) ((DefaultOAuth2User)authentication.getPrincipal()).getAttributes().get("properties");
        String name = (String) properties.get("nickname");

		/*
		 * String token = JWT.create() .withClaim("id", id) .withClaim("name", name)
		 * .withExpiresAt(new Date(System.currentTimeMillis() +
		 * SecurityConstants.EXPIRATION_TIME))
		 * .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
		 * 
		 * res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX
		 * + token);
		 */
	}

}
