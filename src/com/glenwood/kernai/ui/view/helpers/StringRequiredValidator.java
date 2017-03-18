package com.glenwood.kernai.ui.view.helpers;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.fieldassist.ControlDecoration;

public class StringRequiredValidator implements IValidator {

	private final String errorText;
    final ControlDecoration controlDecoration;
	
    public StringRequiredValidator(String errorText)
    {
        super();
        this.errorText = errorText;
        this.controlDecoration = null;
    }
    
    
    public StringRequiredValidator(String errorText, ControlDecoration controlDecoration) 
    {
        super();
        this.errorText = errorText;
        this.controlDecoration = controlDecoration;
    }
    
    
	@Override
    public IStatus validate(Object value) {
        if (value instanceof String) {
            String text = (String) value;
            if (text.trim().length() == 0) {
            	if(this.controlDecoration != null)
            	{
            		controlDecoration.show();
            	}
                return ValidationStatus.error(errorText);
            }
        }
        if(this.controlDecoration != null)
        {
        	controlDecoration.hide();
        }
        return Status.OK_STATUS;
    }

}
