package com.lzrd.Exception;


public class LzrdException extends Exception {

    private static final long serialVersionUID = -7913153372904574437L;

	private int errorCode;

	public LzrdException(String message) {
		super(message);
	}

	public LzrdException(Throwable cause) {
		super(cause);
	}


	public LzrdException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

    public LzrdException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

	public LzrdException(String message, Throwable cause) {
		super(message, cause);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
