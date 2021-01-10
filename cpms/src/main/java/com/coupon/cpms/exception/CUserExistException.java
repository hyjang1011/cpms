/**
 * 
 */
package com.coupon.cpms.exception;

/**
 * @author admin-PC
 *
 */
public class CUserExistException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6174658206339572789L;

	public CUserExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public CUserExistException(String msg) {
        super(msg);
    }

    public CUserExistException() {
        super();
    }
}
