package com.glenwood.kernai.ui.view.helpers;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

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
	
	public void layoutEditLabel(Label label)
	{
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.FILL, SWT.CENTER).applyTo(label);
	}
	
	public void layoutEditEditor(Control control)
	{
		GridDataFactory.fillDefaults().grab(true, false).applyTo(control);
	}
	
	public void layoutMultiLineText(Text text)
	{
		GridDataFactory.fillDefaults().grab(true, true).applyTo(text);
	}
	
	public Text getTextEditor(Composite parent)
	{
		return new Text(parent, SWT.LEFT | SWT.SINGLE | SWT.BORDER);
	}
	
	public Text getMultiLineTextEditor(Composite parent)
	{
		return new Text(parent, SWT.LEFT | SWT.MULTI | SWT.BORDER);
	}
	
	public void layoutMasterDetailCaption(Label label)
	{
		GridDataFactory.fillDefaults().span(2, 1).applyTo(label);
	}
	
	public TableViewerColumn getListColumn(TableViewer listViewer, String columnText)
	{
		return this.getListColumn(listViewer, columnText, SWT.LEFT);
	}
	
	public TableViewerColumn getListColumn(TableViewer listViewer, String columnText, int style)
	{
		TableViewerColumn column = new TableViewerColumn(listViewer, SWT.LEFT);
		column.getColumn().setText(columnText);
		column.getColumn().setResizable(false);
		column.getColumn().setMoveable(false);
		return column; 
	}

}
