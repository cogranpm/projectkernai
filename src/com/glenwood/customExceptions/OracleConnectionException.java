package com.glenwood.customExceptions;

@SuppressWarnings("serial")
public class OracleConnectionException extends RuntimeException {
	
	public OracleConnectionException()
	{
		super("Error connecting to Oracle database");
	}

	public OracleConnectionException(String message)
	{
		super(message);
	}
	
	public OracleConnectionException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public OracleConnectionException(Throwable cause)
	{
		super(cause);
	}
	
	public OracleConnectionException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
	}
}
