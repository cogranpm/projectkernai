package com.glensoft.ui.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.glensoft.data.entity.Model;
import com.glensoft.ui.abstraction.IModelView;
import com.glensoft.ui.presenter.ModelViewPresenter;
import com.glensoft.ui.viewmodel.ModelViewModel;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.beans.BeanProperties;

public class ModelView extends Composite implements IModelView{
	private DataBindingContext m_bindingContext;
	
	private ModelViewPresenter presenter;
	private ModelViewModel model;
	
	private Table table;
	private Text text;
	
	public ModelViewPresenter getPresenter()
	{
		return this.presenter;
	}
	
	public ModelView(Composite parent, int style) {
		super(parent, style);
		model = new ModelViewModel();
		/*
		Model domain = new Model();
		domain.setName("Glensoft");
		model.setCurrentModel(domain);
		*/

		presenter = new ModelViewPresenter(this, model);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		/*
		Composite modelListContainer = new Composite(tabModel, SWT.NONE);
		tbModel.setControl(modelListContainer);
		modelListContainer.setLayout(new GridLayout(1, false));
		modelEdit.setLayout(new FillLayout(SWT.HORIZONTAL));
		*/
		
		CTabFolder tabFolder = new CTabFolder(this, SWT.BORDER);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tbtmNewItem = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("New Item");
		
		
		Composite modelEdit = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem.setControl(modelEdit);
		modelEdit.setLayout(new GridLayout(2, false));
		
		Label lblNewLabel = new Label(modelEdit, SWT.NONE);
		lblNewLabel.setText("&Name");
		
		text = new Text(modelEdit, SWT.BORDER);
		m_bindingContext = initDataBindings();
		
	}
	

	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void drawModels() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addModel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteModel() {
		// TODO Auto-generated method stub
		
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(text);
		IObservableValue currentModelnamePresentergetModelObserveValue = BeanProperties.value("name").observe(presenter.getModel().getCurrentModel());
		bindingContext.bindValue(observeTextTextObserveWidget, currentModelnamePresentergetModelObserveValue, null, null);
		//
		return bindingContext;
	}
}
