/* this is a hardwired test of the combo box in grid requirement */
package com.glensoft.ui.view.grid;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;

import com.glensoft.data.entity.Attribute;
import com.glensoft.data.entity.ListDetail;


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
		ComboBoxViewerCellEditor editor = new ComboBoxViewerCellEditor(viewer.getTable(), SWT.BORDER);
		editor.setContentProvider(new ArrayContentProvider());
		editor.setLabelProvider(new ListLookupLabelProvider());
		editor.setInput(lookups);
		return editor;
	}
	
	
	@Override
	protected Object getValue(Object element) {
		Attribute attribute = (Attribute)element;
		String dataType = attribute.getDataType();
		for(ListDetail listDetail : lookups)
		{
			if(listDetail.getKey().equalsIgnoreCase(dataType))
			{
				return listDetail.getLabel();
			}
				
		}
		return "";
		
	}
	
	
	@Override
	protected void setValue(Object element, Object value) {
		if (element == null)
		{
			return;
		}
		Attribute attribute = (Attribute)element;
		ListDetail listDetail = (ListDetail)value;
		attribute.setDataType(listDetail.getKey());
	}

}
