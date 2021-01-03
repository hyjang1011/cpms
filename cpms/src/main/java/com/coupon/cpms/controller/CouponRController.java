/**
 * 
 */
package com.coupon.cpms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coupon.cpms.domain.BoardDTO;
import com.coupon.cpms.domain.CommentDTO;
import com.coupon.cpms.domain.CouponHistDTO;
import com.coupon.cpms.model.response.ListResult;
import com.coupon.cpms.service.BoardService;
import com.coupon.cpms.service.CouponHistService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

/**
 * @author admin-PC
 *
 */

@Api(tags = {"쿠론 관려 API"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/my/coupon")
public class CouponRController extends ParentRController {

	@Autowired
	private CouponHistService couponService;
	
    @ApiOperation(value = "쿠폰 구매내역", notes = "앱 사용자의 쿠폰 구매내역 리스트 조회")
    @GetMapping(value = "/purchase/{pageno}")
    public ListResult<CouponHistDTO> getPurchaseHist(
    		@ApiParam(value = "페이지 번호", required = true) @PathVariable(value = "pageno", required = true) int pageno,
    		//@RequestBody final CouponHistDTO params
            @ApiParam(value = "쿠폰 사용여부", required = false) @RequestParam(required = false) String useYn,
            @ApiParam(value = "유효기간 시작연월", required = false) @RequestParam(required = false) String exprtnStartYm,
            @ApiParam(value = "유효기간 종료연월", required = false) @RequestParam(required = false) String exprtnEndYm
    ) {
    	CouponHistDTO couponHistDTO = new CouponHistDTO();
    	couponHistDTO.setCurrentPageNo(pageno);
    	
    	if("".equals(exprtnStartYm)) {    		
    		couponHistDTO.setExprtnStartYm(exprtnStartYm);
    	}
    	if("".equals(exprtnEndYm)) {
    		couponHistDTO.setExprtnEndYm(exprtnEndYm);
    	}
    	
		List<CouponHistDTO> memberCouponHistList = couponService.getCouponPurchaseList(couponHistDTO);
    	
    	return responseService.getListResult(memberCouponHistList);
    }
}
