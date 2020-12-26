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
public class CommentDTO extends CommonDTO {

	private Long commentUno;

	private Long bbsUno;

	private String content;

	private String registUno;

}
