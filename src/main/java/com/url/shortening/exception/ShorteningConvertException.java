package com.url.shortening.exception;

public class ShorteningConvertException extends RuntimeException {
	public ShorteningConvertException() {
	}

	public ShorteningConvertException(String message) {
		super(message);
	}

	public ShorteningConvertException(String message, Throwable cause) {
		super(message, cause);
	}
}
