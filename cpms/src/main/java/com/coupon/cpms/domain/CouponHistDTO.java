/**
 * 
 */
package com.coupon.cpms.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * @author admin-PC
 *
 */

@Getter
@Setter
public class CouponHistDTO extends CommonDTO {

	private Long memberUno;
	private Long couponUno;
	private String couponSn;
	private String couponNm;
	private String downloadUrl;
	private int publishPrice;
	private int discountRate;
	private Long storeUno;
	private String exprtnStartYm;
	private String exprtnEndYm;
	private String couponStatus;	/*쿠폰 상태 => U:사용, N:미사용 */
	private LocalDateTime purchaseDt;
	private LocalDateTime usageDt;
	private String purchaseDate;
	private String usageDate;
}
