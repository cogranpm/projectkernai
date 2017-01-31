package com.glenwood.kernai.ui.view;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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
	private Label lblErrorLabel;
	
	AggregateValidationStatus validationStatus;
	
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
		//Composite keyContainer = new Composite(container, SWT.NONE);
		//keyContainer.setLayout(new FillLayout());
		
		lblErrorLabel = new Label(container, SWT.CENTER);
		lblKey= new Label(container, SWT.NONE);
		lblKey.setText("Key");
		txtKey = new Text(container, SWT.SINGLE | SWT.BORDER);
		lblLabel = new Label(container, SWT.NONE);
		lblLabel.setText("Label");
		txtLabel = new Text(container, SWT.SINGLE | SWT.BORDER);
		
		//
		GridDataFactory.fillDefaults().span(2, 1).applyTo(lblErrorLabel);
		GridDataFactory.fillDefaults().applyTo(lblKey);
		GridDataFactory.fillDefaults().applyTo(lblLabel);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(txtKey);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(txtLabel);
		
		//lblErrorLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1 ));
		/*
		lblKey.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1 ));
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1 ));
		lblLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1 ));
		txtLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1 ));
		*/
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
                  //return ValidationStatus.ok();
                  return Status.OK_STATUS;
                }
                return ValidationStatus.error("Key and Label must be entered");
            }
            
          };
	     UpdateValueStrategy strategy = new UpdateValueStrategy();
	     strategy.setAfterConvertValidator(validator);
	        
	    Binding keyBinding = ctx.bindValue(keyTarget, key, strategy, null);
	    Binding labelBinding = ctx.bindValue(labelTarget, label, strategy, null);
	    ControlDecorationSupport keyValidator = ControlDecorationSupport.create(keyBinding, SWT.TOP | SWT.LEFT);
        ControlDecorationSupport labelValidater = ControlDecorationSupport.create(labelBinding, SWT.TOP | SWT.LEFT);
        final IObservableValue errorObservable = WidgetProperties.text().observe(lblErrorLabel);
        validationStatus = new AggregateValidationStatus(ctx.getBindings(), AggregateValidationStatus.MAX_SEVERITY); 
        ctx.bindValue(errorObservable, validationStatus, null, null);
       
		
		return container;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		
		 Button okButton = this.getButton(IDialogConstants.OK_ID);
	     IObservableValue<Button> okBtnTarget = WidgetProperties.enabled().observe(okButton);
	     IConverter converter = IConverter.create(IStatus.class, Boolean.TYPE, (daStatus)-> new Boolean(((IStatus)daStatus).isOK()));
	     ctx.bindValue(okBtnTarget, validationStatus, new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER), UpdateValueStrategy.create(converter));
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
