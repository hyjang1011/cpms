/**
 * 
 */
package com.coupon.cpms.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.coupon.cpms.configuration.oauth.provider.KakaoOAuth2User;
import com.coupon.cpms.handler.MyOAuth2SuccessHandler;

import lombok.RequiredArgsConstructor;

/**
 * @author admin-PC
 *
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disables cors and csrf
        http
                .cors().and()
                .csrf()
                .disable();

        // authenticate
        http.authorizeRequests()
	                .antMatchers("/my").permitAll()
	                //.antMatchers("/").permitAll()
	                .antMatchers("/oauth2/**").permitAll()
	                .antMatchers("/login/**").permitAll()
	                .anyRequest().authenticated()
	            .and()
	            	.csrf()
	            	.ignoringAntMatchers("/board/**")
                .and()
                	.oauth2Login()
                		.userInfoEndpoint()
                		.customUserType(KakaoOAuth2User.class , "kakao");
                	/*
                	 * Spring Security는 기본적으로 세션 기반으로 동작합니다. 
                	 * 즉, 로그인 상태를 서버에서 관리합니다. 하지만, stateless로 만들고 싶은 경우가 있습니다. 
                	 * 이럴 때는 OAuth2 인증 처리 후 실행되는 success handler를 커스터마이징하면 됩니다.
                	 * https://momentjin.tistory.com/144 
                	 * */
                	//.successHandler(new MyOAuth2SuccessHandler());	// Success Handler를 설정
        
//                .userInfoEndpoint()
//                .customUserType(KakaoOAuth2User.class, "kakao").and()

        // disables session creation on Spring Security
//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
