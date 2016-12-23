/******************
 * allows database to be saved in a custom location
 */
package com.glensoft.data.persistence;

import java.io.File;

import com.couchbase.lite.JavaContext;

public class CustomContext extends JavaContext  {
	
	private final String COMPANYNAME = "glenwood";
	
	@Override
	public File getFilesDir() {
		return new File(System.getProperty("user.home") + System.getProperty("file.separator") + COMPANYNAME);
	}

}
