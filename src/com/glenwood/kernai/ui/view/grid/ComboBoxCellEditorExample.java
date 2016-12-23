/* this is a hardwired test of the combo box in grid requirement */
package com.glenwood.kernai.ui.view.grid;

import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;

import com.glenwood.kernai.data.entity.Attribute;
import com.glenwood.kernai.data.entity.ListDetail;


public class ComboBoxCellEditorExample extends EditingSupport{
	
	private TableViewer viewer;
	private List<ListDetail> lookups;
	
	public ComboBoxCellEditorExample(TableViewer viewer, List<ListDetail> lookups)
	{
		super(viewer);
		this.lookups = lookups;
		this.viewer = viewer;
	}
	
	public ComboBoxCellEditorExample(TableViewer viewer)
	{
		super(viewer);
	}
	
	
	@Override
	protected boolean canEdit(Object arg0) {
	
		return true;
	}
	
	
	@Override
	protected CellEditor getCellEditor(Object element) {
		String[] dataTypes = new String[2];
		dataTypes[0] = "String";
		dataTypes[1] = "Boolean";
		ComboBoxCellEditor editor = new ComboBoxCellEditor(this.viewer.getTable(), dataTypes, SWT.READ_ONLY);
		return editor;
		/*
		ComboBoxViewerCellEditor editor = new ComboBoxViewerCellEditor(viewer.getTable(), SWT.BORDER);
		editor.setContentProvider(new ArrayContentProvider());
		editor.setLabelProvider(new ListLookupLabelProvider());
		editor.setInput(lookups);
		return editor;
		*/
	}
	
	
	@Override
	protected Object getValue(Object element) {
		Attribute attribute = (Attribute)element;
		if("string".equalsIgnoreCase(attribute.getDataType()))
		{
			return 0;
		}
		return 1;
		/*

		String dataType = attribute.getDataType();
		for(ListDetail listDetail : lookups)
		{
			if(listDetail.getKey().equalsIgnoreCase(dataType))
			{
				return listDetail.getLabel();
			}
				
		}
		return "";
		*/
		
	}
	
	
	@Override
	protected void setValue(Object element, Object value) {
		
		if (element == null)
		{
			return;
		}
		Attribute attribute = (Attribute)element;
		if((Integer)value == 0)
		{
			attribute.setDataType("string");
		}
		else
		{
			attribute.setDataType("bool");
		}
		viewer.update(element, null);
		//this.viewer.refresh();
		/*
		ListDetail listDetail = (ListDetail)value;
		attribute.setDataType(listDetail.getKey());
		*/
	}

}
