/**
 * 
 */
package com.coupon.cpms.service;

import java.util.List;

import com.coupon.cpms.domain.CouponHistDTO;

/**
 * @author admin-PC
 *
 */
public interface CouponHistService {

	public List<CouponHistDTO> getCouponPurchaseList(CouponHistDTO params);

	public List<CouponHistDTO> getCouponUsageList(CouponHistDTO params);
}
