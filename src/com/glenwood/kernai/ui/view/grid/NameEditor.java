package com.glenwood.kernai.ui.view.grid;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import com.glenwood.kernai.data.entity.Attribute;

public class NameEditor extends EditingSupport {
	
	private final TableViewer viewer;
	private final CellEditor editor;

	public NameEditor(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
		this.editor = new TextCellEditor(viewer.getTable());

	}
	
	
	@Override
	protected CellEditor getCellEditor(Object element) {
		return editor;
	}
	
	
	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		return ((Attribute)element).getName();

	}
	
	@Override
	protected void setValue(Object element, Object userInputValue) {
		((Attribute)element).setName(String.valueOf(userInputValue));
		viewer.update(element, null);
	}
	
}
