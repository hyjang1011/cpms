/**
 * 
 */
package com.coupon.cpms;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coupon.cpms.constant.Method;
import com.coupon.cpms.util.UiUtils;

/**
 * @author hyjang
 *
 */

@Controller
public class HelloController extends UiUtils {
	
	@RequestMapping("/")
	public String index(Model model) {
		//return "hello world!";
		return showMessageWithRedirect(null, "/board/list.do", Method.GET, null, model);
	}
	
	/*
	 * @GetMapping public String
	 * getMyAuthenticationFromSession(@AuthenticationPrincipal OAuth2User
	 * oAuth2User) { return oAuth2User.toString(); }
	 */
}
