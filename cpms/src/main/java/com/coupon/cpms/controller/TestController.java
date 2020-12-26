/**
 * 
 */
package com.coupon.cpms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin-PC
 *
 */

@RestController
public class TestController {
	
	@GetMapping(value = "/message")
	public String testByResponseBody() {
		String message = "안녕하세요. 잠시 후에 화면에서 만나요!";
		return message;
	}
	
	@GetMapping(value = "/members")
	public Map<Integer, Object> testByResponseBodyMap() {

		Map<Integer, Object> members = new HashMap<>();

		for (int i = 1; i <= 20; i++) {
			Map<String, Object> member = new HashMap<>();
			member.put("idx", i);
			member.put("nickname", i + "길동");
			member.put("height", i + 20);
			member.put("weight", i + 30);
			members.put(i, member);
		}

		return members;
	}
}
