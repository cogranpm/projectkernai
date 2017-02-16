package com.glenwood.customExceptions;

@SuppressWarnings("serial")
public class EntityInstantiationError extends RuntimeException {
	
	public EntityInstantiationError()
	{
		super("Class could not be instantiated, does it have a public noargs constructor");
	}

	public EntityInstantiationError(String message)
	{
		super(message);
	}
	
	public EntityInstantiationError(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public EntityInstantiationError(Throwable cause)
	{
		super(cause);
	}
	
	public EntityInstantiationError(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
	}

}
