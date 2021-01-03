/**
 * 
 */
package com.coupon.cpms.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hyjang
 *
 */

@Data
public class JsonCommonResult {

    @ApiModelProperty(value = "응답 성공여부 : true/false")
    private boolean status;

    @ApiModelProperty(value = "http 응답코드")
    private int code;

    @ApiModelProperty(value = "응답 메시지")
    private String msg;
}
