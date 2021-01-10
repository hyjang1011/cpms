/**
 * 
 */
package com.coupon.cpms.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.coupon.cpms.model.response.JsonCommonResult;
import com.coupon.cpms.service.ResponseService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author admin-PC
 *
 */

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

	   private final ResponseService responseService;

	    private final MessageSource messageSource;

	    @ExceptionHandler(Exception.class)
	    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	    protected JsonCommonResult defaultException(HttpServletRequest request, Exception e) {
	        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
	        return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg"));
	    }

	    @ExceptionHandler(CUserNotFoundException.class)
	    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	    protected JsonCommonResult userNotFound(HttpServletRequest request, CUserNotFoundException e) {
	        return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
	    }

	    @ExceptionHandler(CEmailSigninFailedException.class)
	    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	    protected JsonCommonResult emailSigninFailed(HttpServletRequest request, CEmailSigninFailedException e) {
	        return responseService.getFailResult(Integer.valueOf(getMessage("emailSigninFailed.code")), getMessage("emailSigninFailed.msg"));
	    }

	    @ExceptionHandler(CAuthenticationEntryPointException.class)
	    @ResponseStatus(HttpStatus.UNAUTHORIZED)
	    public JsonCommonResult authenticationEntryPointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
	        return responseService.getFailResult(Integer.valueOf(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
	    }

	    @ExceptionHandler(AccessDeniedException.class)
	    @ResponseStatus(HttpStatus.UNAUTHORIZED)
	    public JsonCommonResult accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
	        return responseService.getFailResult(Integer.valueOf(getMessage("accessDenied.code")), getMessage("accessDenied.msg"));
	    }

	    @ExceptionHandler(CCommunicationException.class)
	    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	    public JsonCommonResult communicationException(HttpServletRequest request, CCommunicationException e) {
	        return responseService.getFailResult(Integer.valueOf(getMessage("communicationError.code")), getMessage("communicationError.msg"));
	    }

	    @ExceptionHandler(CUserExistException.class)
	    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	    public JsonCommonResult communicationException(HttpServletRequest request, CUserExistException e) {
	        return responseService.getFailResult(Integer.valueOf(getMessage("existingUser.code")), getMessage("existingUser.msg"));
	    }

	    // code정보에 해당하는 메시지를 조회합니다.
	    private String getMessage(String code) {
	        return getMessage(code, null);
	    }
	    // code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
	    private String getMessage(String code, Object[] args) {
	        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	    }
}
