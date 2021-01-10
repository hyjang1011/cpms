/**
 * 
 */
package com.coupon.cpms.service;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.coupon.cpms.domain.UserInfoDTO;
import com.coupon.cpms.mapper.UserInfoMapper;
import com.coupon.cpms.model.UserInfo;

/**
 * @author admin-PC
 *
 */

@Service
public class MyOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Override
	public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
		// TODO Auto-generated method stub
		//throw new NotImplementedException();
		return null;
	}

	@Override
	public void saveAuthorizedClient(OAuth2AuthorizedClient oAuth2AuthorizedClient, Authentication authentication) {
        String providerType = oAuth2AuthorizedClient.getClientRegistration().getRegistrationId();
        OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        //String oauthId = String.valueOf(oauth2User.getAttributes().get("id"));
        //String nickname = (String) ((LinkedHashMap) ((LinkedHashMap) oauth2User.getAttribute("kakao_account")).get("profile")).get("nickname");
        String oauthId = oauth2User.getName();
        String nickname = oauth2User.getAttribute("nickname");

        UserInfo userInfo = userInfoMapper.selectUserInfoByOAuthId(oauthId);
        
        if(userInfo != null) {
        	userInfoMapper.updateUserInfoByOAuthId(userInfo);
        }else {        	
        	userInfo = new UserInfo();
        	
        	userInfo.setOauthId(oauthId);
        	userInfo.setNickname(nickname);
        	userInfo.setProviderType(providerType);
        	userInfo.setAccessToken(accessToken.getTokenValue());

        	userInfoMapper.insertUserInfo(userInfo);
        }
        
	}

	@Override
	public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
		// TODO Auto-generated method stub
		//throw new NotImplementedException();
	}

}
