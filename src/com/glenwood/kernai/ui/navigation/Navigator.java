/* this class needs to hold the current view and presenter
 * so that calls from the generic save and delete can be forwarded
 * therefore presenters need to implement standard interface and so do views
 */

package com.glenwood.kernai.ui.navigation;

import org.eclipse.swt.SWT;

import com.glenwood.kernai.ui.MainWindow;
import com.glenwood.kernai.ui.abstraction.INavigator;
import com.glenwood.kernai.ui.view.AttributeView;

public class Navigator implements INavigator {
	
	public void loadProjectView(String projectId)
	{
		MainWindow.mainShell.clearEditRegion();
		AttributeView view = new AttributeView(MainWindow.mainShell.getEditRegion(), SWT.NONE);
		
		
		//ProjectView view = new ProjectView(MainWindow.mainShell.getEditRegion(), SWT.NONE);
		//view.getPresenter().loadProjects();
		/*
		ModelView view = new ModelView(MainWindow.mainShell.getEditRegion(), SWT.NONE);
		view.getPresenter().loadModels(projectId);
		*/
		MainWindow.mainShell.getEditRegion().layout();
	}


}
