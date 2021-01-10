/**
 * 
 */
package com.coupon.cpms.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.coupon.cpms.configuration.jwt.JwtAuthenticationFilter;
import com.coupon.cpms.configuration.jwt.JwtTokenProvider;
import com.coupon.cpms.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * @author admin-PC
 *
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;
	
    @Override
    public void configure(WebSecurity web) throws Exception {
        //super.configure(web);
    	web.ignoring().antMatchers("/css/**", "/img/**", "/plugin/**", "/scripts/**", "/fonts/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
	        .csrf().disable()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // disables session creation on Spring Security
	        	.and()
        	.authorizeRequests()	//authenticate 접근 권한 설정
	        	//.antMatchers("/my").permitAll()
	            .antMatchers("/oauth2/**").permitAll()	//누구나 접근 허용
	            .antMatchers("/login", "/login/**").permitAll()
	            .antMatchers("/signup/**").permitAll()
	            .antMatchers("/user/**").permitAll()
	            .antMatchers("/board/**").permitAll()
	            .antMatchers("/v1/signin", "/v1/signin/**", "/v1/signup", "/v1/signup/**", "/social/**").permitAll()
	            .antMatchers("/my/**").hasRole("USER")	//USER, ADMIN만 접근가능
	            .antMatchers("/admin").hasRole("ADMIN")	//ADMIN만 접근가능
	            .anyRequest().authenticated()	//나머지 요청은 권한의 종류 상관없이 권한부여 받아야만 접근 가능
	            .and()
	            
//	        .formLogin()	//로그인 관련 설정
//	        	.loginPage("/login")	//로그인 페이지
//	        	.defaultSuccessUrl("/board/list.do")	//로그인 성공 후 리다이렉트 주소
//	            	//.csrf()
//	            	//.ignoringAntMatchers("/board/**")
//                .and()
                
            .logout()
            	.logoutSuccessUrl("/login")	//로그아웃 성공 후 리다이렉트 주소
            	.invalidateHttpSession(true)	//로그아웃 이후 세션 삭제여부
            	.and()
        	//.oauth2Login()
        	//	.userInfoEndpoint()
        	//    .customUserType(KakaoOAuth2User.class , "kakao")
                	/*
                	 * Spring Security는 기본적으로 세션 기반으로 동작합니다. 
                	 * 즉, 로그인 상태를 서버에서 관리합니다. 하지만, stateless로 만들고 싶은 경우가 있습니다. 
                	 * 이럴 때는 OAuth2 인증 처리 후 실행되는 success handler를 커스터마이징하면 됩니다.
                	 * https://momentjin.tistory.com/144 
                	 * */
                	//.successHandler(new MyOAuth2SuccessHandler());	// Success Handler를 설정
        
//                .userInfoEndpoint()
//                .customUserType(KakaoOAuth2User.class, "kakao").and()
            
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
        
        ;
    }

    
    /**
     * 로그인할 때 필요한 정보를 가져옴
     */
//    
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//    	// 해당 서비스(userService)에서는 UserDetailsService를 implements해서 loadUserByUsername() 구현해야함
//    	auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
//    }
//    
//    
//    @Bean 
//    public PasswordEncoder passwordEncoder() {
//      return new BCryptPasswordEncoder();
//      //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//    
    
}
