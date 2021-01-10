/**
 * 
 */
package com.coupon.cpms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.coupon.cpms.configuration.jwt.JwtTokenProvider;
import com.coupon.cpms.domain.UserInfoDTO;
import com.coupon.cpms.exception.CEmailSigninFailedException;
import com.coupon.cpms.model.UserInfo;
import com.coupon.cpms.service.UserService;

/**
 * @author admin-PC
 *
 */

@Controller
public class UserController {

	@Autowired
    private Environment env;
    
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired	
	protected JwtTokenProvider jwtTokenProvider;
	
    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;
	
	/**
	 * 로그인 화면 
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/login")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        StringBuilder loginUrl = new StringBuilder()
                .append(env.getProperty("spring.social.kakao.url.login"))
                .append("?client_id=").append(kakaoClientId)
                .append("&response_type=code")
                .append("&redirect_uri=").append(baseUrl).append(kakaoRedirect);

        model.addAttribute("loginUrl", loginUrl);
		
		return "login";
	}
	
	@PostMapping("/login")
	public String login(UserInfoDTO userInfoDTO, Model model) {

        UserInfo user = userService.findByUid(userInfoDTO.getEmail());
        
        if(user == null) {
        	throw new CEmailSigninFailedException();
        }
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //userInfoDTO.setPasswrd(encoder.encode(userInfoDTO.getPasswrd()));
        
        if (!passwordEncoder.matches("{bcrypt}" + userInfoDTO.getPasswrd(), "{bcrypt}" + user.getPassword())) {
            //throw new CEmailSigninFailedException();
        }

        model.addAttribute("token", jwtTokenProvider.createToken(String.valueOf(user.getUserUno()), user.getRoles()));
		
		return "user/main";
	}
	
	 /**
	  * 회원가입
	  * 
	 * @param userInfoDTO
	 * @return
	 */
	@PostMapping("/user")
	public String signup(UserInfoDTO userInfoDTO) {
		userService.saveUserInfoDTO(userInfoDTO);
		return "redirect:/login";
	}
	  
	 /**
	  * 로그아웃 
	  * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
		return "redirect:/login";
	}
	
}
