package com.box.common;

public class GlobalException extends Exception {
	private static final long serialVersionUID = 1L;

	public GlobalException(Object message) {
        super(message.toString());
    }
}
