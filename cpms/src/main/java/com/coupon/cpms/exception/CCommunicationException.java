/**
 * 
 */
package com.coupon.cpms.exception;

/**
 * @author admin-PC
 *
 */
public class CCommunicationException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6813528036056675411L;

	public CCommunicationException(String msg, Throwable t) {
        super(msg, t);
    }

    public CCommunicationException(String msg) {
        super(msg);
    }

    public CCommunicationException() {
        super();
    }
}
