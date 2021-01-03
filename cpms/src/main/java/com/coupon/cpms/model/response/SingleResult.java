/**
 * 
 */
package com.coupon.cpms.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hyjang
 *
 */

@Getter
@Setter
public class SingleResult<T> extends JsonCommonResult {
	private T data;
}
