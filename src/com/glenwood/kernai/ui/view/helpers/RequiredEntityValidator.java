package com.glenwood.kernai.ui.view.helpers;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.fieldassist.ControlDecoration;

public class RequiredEntityValidator implements IValidator {
	
	private final String errorText;
    private ControlDecoration controlDecoration;
	
    public void setControlDecoration(ControlDecoration controlDecoration)
    {
    	this.controlDecoration = controlDecoration;
    }
    
    public RequiredEntityValidator(String errorText)
    {
        super();
        this.errorText = errorText;
        this.controlDecoration = null;
    }
    
    
    public RequiredEntityValidator(String errorText, ControlDecoration controlDecoration) 
    {
        super();
        this.errorText = errorText;
        this.controlDecoration = controlDecoration;
    }
    
    
	@Override
    public IStatus validate(Object value) {

        if (null == value) {
        	if(this.controlDecoration != null)
        	{
        		controlDecoration.show();
        	}
            return ValidationStatus.error(errorText);
        }

        if(this.controlDecoration != null)
        {
        	controlDecoration.hide();
        }
        return Status.OK_STATUS;
    }

}
