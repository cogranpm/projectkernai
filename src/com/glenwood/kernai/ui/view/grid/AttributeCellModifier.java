package com.glenwood.kernai.ui.view.grid;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.Viewer;

import com.glenwood.kernai.data.entity.Attribute;

public class AttributeCellModifier implements ICellModifier {

	private Viewer viewer;
	
	public AttributeCellModifier(Viewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public boolean canModify(Object element, String property) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object getValue(Object element, String property) {
		// TODO Auto-generated method stub
		Attribute attribute = (Attribute)element;
		if ("Name".equalsIgnoreCase(property))
		{
			return attribute.getName();
		}
		else if("DataType".equalsIgnoreCase(property))
		{
			return attribute.getDataType();
		}
		return null;
	}

	@Override
	public void modify(Object element, String property, Object value) {
		// TODO Auto-generated method stub
		Attribute attribute = (Attribute)element;
		if ("Name".equalsIgnoreCase(property))
		{
			attribute.setName(value.toString());
		}
		else if("DataType".equalsIgnoreCase(property))
		{
			attribute.setDataType(value.toString());
		}
		viewer.refresh();

	}

}
