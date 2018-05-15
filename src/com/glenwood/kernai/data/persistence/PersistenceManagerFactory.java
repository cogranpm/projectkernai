package com.glenwood.kernai.data.persistence;

import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.ui.ApplicationData;

public class PersistenceManagerFactory {
	
	public static IPersistenceManager getPersistenceManager(String type)
	{
		if ("couchbase".equalsIgnoreCase(type))
		{
			return ApplicationData.instance().getPersistenceManager();
		}
		else
		{
			return null;
		}
	}

}
