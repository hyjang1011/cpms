/**
 * 
 */
package com.coupon.cpms.domain;

import java.time.LocalDateTime;

import com.coupon.cpms.util.Criteria;
import com.coupon.cpms.util.PaginationInfo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author admin-PC
 *
 */

@Getter
@Setter
public class CommonDTO extends Criteria {
	
	/** 페이징 정보 */
	private PaginationInfo paginationInfo;

	/** 삭제 여부 */
	private String deleteYn;

	/** 등록일 */
	private LocalDateTime registDt;

	/** 수정일 */
	private LocalDateTime updateDt;

	/** 삭제일 */
	private LocalDateTime deleteDt;
}
