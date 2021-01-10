/**
 * 
 */
package com.coupon.cpms.exception;

/**
 * @author admin-PC
 *
 */

public class CAuthenticationEntryPointException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4062961984281552236L;

	public CAuthenticationEntryPointException(String msg, Throwable t) {
        super(msg, t);
    }

    public CAuthenticationEntryPointException(String msg) {
        super(msg);
    }

    public CAuthenticationEntryPointException() {
        super();
    }
}
