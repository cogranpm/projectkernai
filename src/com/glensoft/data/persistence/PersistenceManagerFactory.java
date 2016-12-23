package com.glensoft.data.persistence;

import com.glensoft.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.ui.MainWindow;

public class PersistenceManagerFactory {
	
	public static IPersistenceManager getPersistenceManager(String type)
	{
		if ("couchbase".equalsIgnoreCase(type))
		{
			return MainWindow.persistenceManager;
		}
		else
		{
			return null;
		}
	}

}
