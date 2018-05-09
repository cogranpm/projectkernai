/******************
 * allows database to be saved in a custom location
 */
package com.glenwood.kernai.data.persistence;

import java.io.File;

import com.couchbase.lite.JavaContext;
import com.glenwood.kernai.ui.ApplicationData;

public class CustomContext extends JavaContext  {
	
		
	@Override
	public File getFilesDir() {
		String homedir =System.getProperty("user.home") + System.getProperty("file.separator") + ApplicationData.COMPANY_NAME; 
		System.out.println(homedir);
		return new File(homedir);
	}

}
