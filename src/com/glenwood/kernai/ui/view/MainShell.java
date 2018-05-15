/* main shell of the application, has regions for all the composites to use */
package com.glenwood.kernai.ui.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;

/**
 * @deprecated  moved to a tab based navigation instead of tree
 * didn't need this anymore using the MainWindow instead
 */
@Deprecated
public class MainShell extends Composite {
	
	//Composite topRegion;
	//Composite mainRegion;
	//Composite leftRegion;
	//Composite editRegion;
	
	private CTabFolder mainTabFolder;
	public CTabFolder getMainTabFolder()
	{
		return this.mainTabFolder;
	}
	
	private CTabItem masterPropertiesTabItem;
	

	public CTabItem getMasterPropertiesTabItem() {
		return masterPropertiesTabItem;
	}

	public MainShell(Composite parent, int style) {
		super(parent, style);
		//setLayout(new FillLayout(SWT.HORIZONTAL));
		
		
		/*
		SashForm sashTop = new SashForm(this, SWT.BORDER | SWT.VERTICAL);
		
		topRegion = new Composite(sashTop, SWT.NONE);
		topRegion.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		topRegion.setLayout(new FillLayout(SWT.HORIZONTAL));
		*/
	//	mainRegion = new Composite(parent, SWT.NONE);
	// mainRegion.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		/*
		SashForm sashMain = new SashForm(mainRegion, SWT.BORDER);
		
		leftRegion = new Composite(sashMain, SWT.BORDER);
		leftRegion.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		leftRegion.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		editRegion = new Composite(sashMain, SWT.BORDER);
		editRegion.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
		editRegion.setLayout(new FillLayout(SWT.HORIZONTAL));
		sashMain.setWeights(new int[] {126, 317});
		sashTop.setWeights(new int[] {1, 10});
		*/
		mainTabFolder = new CTabFolder(parent, SWT.TOP);
		CTabItem item = new CTabItem(mainTabFolder, SWT.NONE);
		item.setText("Getting Started");
		item = new CTabItem(mainTabFolder, SWT.NONE);
		item.setText("Master Properties");
		this.masterPropertiesTabItem = item;
		item  = new CTabItem(mainTabFolder, SWT.NONE);
		item.setText("Models");
		item = new CTabItem(mainTabFolder, SWT.NONE);
		item.setText("Scripting");

	}
	
	public void createContents()
	{
		
	}
	
	/*
	public Composite getLeftRegion()
	{
		return this.leftRegion; 
	}
	
	public Composite getEditRegion()
	{
		return this.editRegion;
	}
	
	public void clearEditRegion()
	{
		for (Control control : this.getEditRegion().getChildren()) {
	        control.dispose();
	    }		
	}
	*/
}
