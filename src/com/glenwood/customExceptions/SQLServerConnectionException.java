package com.glenwood.customExceptions;

@SuppressWarnings("serial")
public class SQLServerConnectionException extends RuntimeException {
	
	public SQLServerConnectionException()
	{
		super("Error connecting to Oracle database");
	}

	public SQLServerConnectionException(String message)
	{
		super(message);
	}
	
	public SQLServerConnectionException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public SQLServerConnectionException(Throwable cause)
	{
		super(cause);
	}
	
	public SQLServerConnectionException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
	}
}
