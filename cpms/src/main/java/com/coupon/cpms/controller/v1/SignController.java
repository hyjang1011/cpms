/**
 * 
 */
package com.coupon.cpms.controller.v1;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coupon.cpms.constant.Role;
import com.coupon.cpms.exception.CEmailSigninFailedException;
import com.coupon.cpms.exception.CUserExistException;
import com.coupon.cpms.exception.CUserNotFoundException;
import com.coupon.cpms.model.UserInfo;
import com.coupon.cpms.model.response.JsonCommonResult;
import com.coupon.cpms.model.response.SingleResult;
import com.coupon.cpms.model.social.KakaoProfile;
import com.coupon.cpms.service.KakaoService;
import com.coupon.cpms.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

/**
 * @author admin-PC
 *
 */

@Api(tags = {"App 회원가입 API"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController extends ParentController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private KakaoService kakaoService;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<Map<String, Object>> signin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String email,
                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
        UserInfo user = userService.findByUid(email);
        
        if(user == null) {
        	throw new CEmailSigninFailedException();
        }
        
        if (!passwordEncoder.matches("{bcrypt}" + password, "{bcrypt}" + user.getPassword())) {
            //throw new CEmailSigninFailedException();
        }

        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("jwt", jwtTokenProvider.createToken(String.valueOf(user.getUserUno()), user.getRoles()));
        
        return responseService.getSingleResult(returnMap);

    }
    
    @ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인을 한다.")
    @PostMapping(value = "/signin/{provider}")
    public SingleResult<String> signinByProvider(
            @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
            @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) {

        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        UserInfo user = userService.findByOAuthIdAndProvider(String.valueOf(profile.getId()), provider).orElseThrow(CUserNotFoundException::new);
        
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getUserUno()), user.getRoles()));
    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public JsonCommonResult signup(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String email,
                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                               @ApiParam(value = "닉네임", required = true) @RequestParam String nickname) {
    	
    	userService.saveUserInfo(UserInfo.builder()
    				.email(email)
    				.passwrd(password)
    				.nickname(nickname)
    				.auth(Role.USER.getKey())
    				.roles(Collections.singletonList("ROLE_USER"))
    				.build()
    			);
        
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입을 한다.")
    @PostMapping(value = "/signup/{provider}")
    public JsonCommonResult signupProvider(@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
                                       @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken,
                                       @ApiParam(value = "이름", required = true) @RequestParam String nickname) {

        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        Optional<UserInfo> user = userService.findByOAuthIdAndProvider(String.valueOf(profile.getId()), provider);
        
        if (user.isPresent()) {
            throw new CUserExistException();
        }

        UserInfo userInfo = UserInfo.builder()
                .oauthId(String.valueOf(profile.getId()))
                .providerType(provider)
                .nickname(nickname)
                .auth(Role.USER.getKey())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        userService.saveUserInfo(userInfo);

        return responseService.getSuccessResult();
    }
}
