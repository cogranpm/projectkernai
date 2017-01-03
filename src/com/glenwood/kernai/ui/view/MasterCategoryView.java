package com.glenwood.kernai.ui.view;



import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.glenwood.kernai.ui.abstraction.IEntityView;
import com.glenwood.kernai.ui.presenter.MasterCategoryViewPresenter;
import com.glenwood.kernai.ui.viewmodel.MasterCategoryViewModel;



public class MasterCategoryView extends Composite implements IEntityView {

	private MasterCategoryViewModel model;
	private MasterCategoryViewPresenter presenter;
	
	public MasterCategoryView(Composite parent, int style) {
		super(parent, style);
		model = new MasterCategoryViewModel();
		presenter = new MasterCategoryViewPresenter(this, model);
		
		/* firstly a list and then an edit view */
		GridLayout layout = new GridLayout(2, false);
		setLayout(layout);
		Label lblName = new Label(this, SWT.NONE);
		lblName.setText("Name");
		Text txtName = new Text(this, SWT.LEFT | SWT.SINGLE | SWT.BORDER);
		lblName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1 ));
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1 ));
		/* load the data */
		presenter.loadModels();
		
	}

	@Override
	public void delete() {
		this.presenter.deleteModel();
		
	}

	@Override
	public void add() {
		this.presenter.addModel();
		
	}

	@Override
	public void save() {
		this.presenter.saveModel();
		
	}
	
	public void refreshList()
	{
		
	}
}
