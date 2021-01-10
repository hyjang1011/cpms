/**
 * 
 */
package com.coupon.cpms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coupon.cpms.model.response.JsonCommonResult;
import com.coupon.cpms.model.response.ListResult;
import com.coupon.cpms.model.response.SingleResult;

/**
 * @author admin-PC
 *
 */

@Service
public class ResponseService {
	
    // enum으로 api 요청 결과에 대한 code, message를 정의합니다.
    public enum CommonResponse {
        SUCCESS(200, "success")
    	, FAIL(-400, "fail");

        int code;
        String msg;

        CommonResponse(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
    
    // 단일건 결과를 처리하는 메소드
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }
    
    // 다중건 결과를 처리하는 메소드
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }
    // 성공 결과만 처리하는 메소드
    public JsonCommonResult getSuccessResult() {
        JsonCommonResult result = new JsonCommonResult();
        setSuccessResult(result);
        return result;
    }
    // 실패 결과만 처리하는 메소드
    public JsonCommonResult getFailResult(int code, String msg) {
        JsonCommonResult result = new JsonCommonResult();
        result.setStatus(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResult(JsonCommonResult result) {
        result.setStatus(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
}
