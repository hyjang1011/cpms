/**
 * 
 */
package com.coupon.cpms.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coupon.cpms.domain.BoardDTO;
import com.coupon.cpms.domain.CouponHistDTO;
import com.coupon.cpms.mapper.BoardMapper;
import com.coupon.cpms.mapper.CouponHistMapper;
import com.coupon.cpms.service.CouponHistService;
import com.coupon.cpms.util.PaginationInfo;

/**
 * @author admin-PC
 *
 */

@Service
public class CouponHistServiceImpl implements CouponHistService {

	@Autowired
	private CouponHistMapper couponHistMapper;
	
	@Override
	public List<CouponHistDTO> getCouponPurchaseList(CouponHistDTO params) {
		List<CouponHistDTO> couponHistList = Collections.emptyList();

		int couponHistTotalCount = couponHistMapper.selectCouponHistCount(params);

		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(couponHistTotalCount);
		
		params.setPaginationInfo(paginationInfo);
		
		if (couponHistTotalCount > 0) {
			couponHistList = couponHistMapper.selectCouponPurchaseList(params);
		}

		return couponHistList;
	}

	@Override
	public List<CouponHistDTO> getCouponUsageList(CouponHistDTO params) {
		// TODO Auto-generated method stub
		return null;
	}

}
