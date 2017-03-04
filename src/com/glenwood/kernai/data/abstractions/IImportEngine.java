package com.glenwood.kernai.data.abstractions;

import java.util.List;

public interface IImportEngine {
	public List<String> getDatabases(IConnection connection);

}
