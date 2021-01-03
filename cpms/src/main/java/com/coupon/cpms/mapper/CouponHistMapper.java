/**
 * 
 */
package com.coupon.cpms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coupon.cpms.domain.CouponHistDTO;

/**
 * @author admin-PC
 *
 */

@Mapper
public interface CouponHistMapper {

	public int selectCouponHistCount(CouponHistDTO params);

	public List<CouponHistDTO> selectCouponPurchaseList(CouponHistDTO params);
	
}
