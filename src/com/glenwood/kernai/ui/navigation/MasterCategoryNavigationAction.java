package com.glenwood.kernai.ui.navigation;

import org.eclipse.swt.SWT;

import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.MainWindow;
import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.abstraction.INavigationMenuAction;
import com.glenwood.kernai.ui.view.MasterCategoryView;

public class MasterCategoryNavigationAction implements INavigationMenuAction {

	@Override
	public void go() {
		loadMasterCategoryView();
	}

	@Override
	public void go(Long id) {

	}

	
	private void loadMasterCategoryView()
	{
		
		/*MainWindow.mainShell.clearEditRegion();
		IEntityView view = new MasterCategoryView(MainWindow.mainShell.getEditRegion(), SWT.NONE);
		*/
		
		/* don't forget to set the current view for the toolbars etc 
		ApplicationData.instance().setCurrentEntityView(view);
		MainWindow.mainShell.getEditRegion().layout();
		*/
	}
}
