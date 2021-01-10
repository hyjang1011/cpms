/**
 * 
 */
package com.coupon.cpms.exception;

/**
 * @author admin-PC
 *
 */
public class CEmailSigninFailedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6225176211509458074L;

	public CEmailSigninFailedException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public CEmailSigninFailedException(String msg) {
		super(msg);
	}
	
	public CEmailSigninFailedException() {
		super();
	}
}
