/**
 * 
 */
package com.coupon.cpms;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hyjang
 *
 */

@RestController
public class HelloController {
	@RequestMapping("/")
	public String index() {
		return "hello world!";
	}
}
