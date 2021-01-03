/**
 * 
 */
package com.coupon.cpms.model.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hyjang
 *
 */

@Getter
@Setter
public class ListResult<T> extends JsonCommonResult {
	private List<T> list;
}
