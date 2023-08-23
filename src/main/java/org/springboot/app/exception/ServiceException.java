package org.springboot.app.exception;

/**
 * @author jmedina
 *
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = 4188230339698051392L;

	public ServiceException(String msg) {
		super(msg);
	}

	public ServiceException(String msg, Exception e) {
		super(msg, e);
	}

}