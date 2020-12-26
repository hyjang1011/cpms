/**
 * 
 */
package com.coupon.cpms.exception;

/**
 * @author admin-PC
 *
 */
public class AttachFileException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8888803167514040002L;

	public AttachFileException(String message) {
		super(message);
	}

	public AttachFileException(String message, Throwable cause) {
		super(message, cause);
	}
}
