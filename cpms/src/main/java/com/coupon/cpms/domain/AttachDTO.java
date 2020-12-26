/**
 * 
 */
package com.coupon.cpms.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author admin-PC
 *
 */

@Getter
@Setter
public class AttachDTO extends CommonDTO {

	/** 파일 번호 (PK) */
	private Long attachUno;

	/** 게시글 번호 (FK) */
	private Long bbsUno;

	/** 원본 파일명 */
	private String originalName;

	/** 저장 파일명 */
	private String saveName;

	/** 파일 크기 */
	private long attachSize;
}
