package com.glenwood.kernai.ui.view;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Tree;

import com.glenwood.kernai.ui.abstraction.INavView;
import com.glenwood.kernai.ui.presenter.NavViewPresenter;
import com.glenwood.kernai.ui.view.navigation.NavigationMenuContentProvider;
import com.glenwood.kernai.ui.view.navigation.NavigationMenuLabelProvider;
import com.glenwood.kernai.ui.viewmodel.NavigationMenu;

public class NavView extends Composite implements INavView{
	
	NavViewPresenter presenter;
	List lstTest;
	Tree menuTree;
	TreeViewer menuTreeViewer;
	
	public NavView(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
		this.presenter = new NavViewPresenter(this);
		setLayout(new GridLayout(1, false));
		/*
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				presenter.test();
			}
		});
		btnNewButton.setText("Test");		
		*/
		
		
		lstTest = new List(this, SWT.BORDER | SWT.V_SCROLL);
		lstTest.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String sel = lstTest.getSelection()[0];
				presenter.loadProject(sel);
				
				/* try to load something in the edit region */
				/*
				MainWindow.mainShell.clearEditRegion();
				//try loading the modelview in the edit region
				
				Display display = Display.getCurrent();
				Color blue = display.getSystemColor(SWT.COLOR_BLUE);
				MainWindow.mainShell.getEditRegion().setBackground(blue);
				*/
				
			}
		});
		lstTest.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		
		//menuTree = new Tree(parent, SWT.BORDER);
		//menuTree.setHeaderVisible(false);
		menuTreeViewer = new TreeViewer(this, SWT.H_SCROLL | SWT.V_SCROLL);
		menuTreeViewer.setContentProvider(new NavigationMenuContentProvider());
		menuTreeViewer.setLabelProvider(new NavigationMenuLabelProvider());

		menuTreeViewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		
		presenter.loadProjects();
		presenter.loadMenus();
	}
	
	public void createContents()
	{

	}
	
	public void renderMenus(NavigationMenu menu)
	{
		menuTreeViewer.setInput(menu);
	}
	
	public void renderProjects(java.util.List<String> results)
	{
		lstTest.removeAll();
		for (String result : results)
		{
			lstTest.add(result);
		}
	}

}
