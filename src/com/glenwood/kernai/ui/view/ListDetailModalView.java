package com.glenwood.kernai.ui.view;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.ui.viewmodel.ListDetailViewModel;

public class ListDetailModalView extends Dialog {

	private ListDetailViewModel model;
	private Text txtKey;
	private Label lblKey;
	private Text txtLabel;
	private Label lblLabel;
	private DataBindingContext ctx;
	private WritableValue<ListDetail> value;
	
	public ListDetailViewModel getModel()
	{
		return this.model;
	}
	
	public void setModel(ListDetailViewModel model)
	{
		this.model = model;
	}
	
	protected ListDetailModalView(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite)super.createDialogArea(parent);
		lblKey= new Label(container, SWT.NONE);
		lblKey.setText("Key");
		txtKey = new Text(container, SWT.SINGLE | SWT.BORDER);
		lblLabel = new Label(container, SWT.NONE);
		lblLabel.setText("Label");
		txtLabel = new Text(container, SWT.SINGLE | SWT.BORDER);
		lblKey.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1 ));
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1 ));
		lblLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1 ));
		txtLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1 ));
		container.setLayout(new GridLayout(2, false));
		ctx = new DataBindingContext();
		value = new WritableValue<ListDetail>();
		value.setValue(this.model.getCurrentItem());
		
		
	   IObservableValue keyTarget = WidgetProperties.text(SWT.Modify).observe(txtKey);
	   IObservableValue key = BeanProperties.value("key").observeDetail(value);
	   
	   IObservableValue labelTarget = WidgetProperties.text(SWT.Modify).observe(txtLabel);
	   IObservableValue label = BeanProperties.value("label").observeDetail(value);
	       
        /* just the validators and decorators in the name field */
        IValidator validator = new IValidator() {
            @Override
            public IStatus validate(Object value) {
                String nameValue = String.valueOf(value).replaceAll("\\s", "");
                if (nameValue.length() > 0){
                  return ValidationStatus.ok();
                }
                return ValidationStatus.error("Key must be entered");
            }
            
          };
	     UpdateValueStrategy strategy = new UpdateValueStrategy();
	     strategy.setAfterConvertValidator(validator);
	        
	    Binding keyBinding = ctx.bindValue(keyTarget, key, strategy, null);
	    Binding labelBinding = ctx.bindValue(labelTarget, label, strategy, null);
		
		return container;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, false);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	
	}
	
	
	@Override
	protected void okPressed() {
		// TODO Auto-generated method stub
		super.okPressed();
	}
	

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("List Item");
	}
	
	
	
}
