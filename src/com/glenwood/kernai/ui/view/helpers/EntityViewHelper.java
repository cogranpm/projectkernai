package com.glenwood.kernai.ui.view.helpers;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;

public class EntityViewHelper {
	
	public GridLayout getViewLayout(int numColumns)
	{
		GridLayout editContainerLayout = new GridLayout(numColumns, false);
		editContainerLayout.marginWidth = 0;
		editContainerLayout.marginHeight = 0;
		return editContainerLayout;
	}
	
	
	public void setViewLayoutData(Control control, boolean grabHorizontal, boolean grabVertical)
	{
		GridDataFactory.fillDefaults().grab(grabHorizontal, grabVertical).applyTo(control);
	}
	
	public void setViewLayoutData(Control control, int horizontalSpan)
	{
		GridDataFactory.fillDefaults().span(horizontalSpan, 1) .applyTo(control);
	}

}
