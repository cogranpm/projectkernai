package com.glenwood.kernai.ui.view.grid;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;

import com.glenwood.kernai.data.entity.Attribute;
import com.glenwood.kernai.data.entity.ListDetail;

public class ComboBoxCellViewerEditorExample extends EditingSupport{

	private TableViewer viewer;
	private List<ListDetail> lookups;
	
	public ComboBoxCellViewerEditorExample(TableViewer viewer, List<ListDetail> lookups)
	{
		super(viewer);
		this.lookups = lookups;
		this.viewer = viewer;
	}
	
	public ComboBoxCellViewerEditorExample(TableViewer viewer)
	{
		super(viewer);
	}
	
	
	@Override
	protected boolean canEdit(Object arg0) {
	
		return true;
	}
	
	
	@Override
	protected CellEditor getCellEditor(Object element) {
		ComboBoxViewerCellEditor editor = new ComboBoxViewerCellEditor(this.viewer.getTable(), SWT.READ_ONLY);

		IStructuredContentProvider contentProvider = new ArrayContentProvider();
		editor.setContentProvider( contentProvider );
		editor.setLabelProvider(new LabelProvider(){
			@Override
			public String getText(Object element)
			{
				ListDetail item = (ListDetail)element;
				return item.getLabel();

			}
		});
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
				return listDetail;
			}
				
		}
		return null;
		
	}
	
	
	@Override
	protected void setValue(Object element, Object value) {
		
		if (element == null)
		{
			return;
		}
		if (value == null)
		{
			return;
		}
		Attribute attribute = (Attribute)element;
		ListDetail listDetail = (ListDetail)value;
		attribute.setDataType(listDetail.getKey());
		
		viewer.update(element, null);

	}

}
