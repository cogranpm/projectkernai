package com.glenwood.kernai.data.modelimport;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.glenwood.kernai.data.abstractions.IConnection;
import com.glenwood.kernai.data.abstractions.IImportEngine;

public class ImportEngineBase implements IImportEngine{
	
	@Override
	public List<String> getDatabases(IConnection connection)
	{
		try {
			DatabaseMetaData  metaData = connection.getConnection().getMetaData();
			int minorVersion = metaData.getDatabaseMinorVersion();
			System.out.println(String.format("MinorVersion %d", minorVersion));
			System.out.println(String.format("Product Name %s", metaData.getDatabaseProductName()));
			System.out.println(String.format("Product Version %s", metaData.getDatabaseProductVersion()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}

}
