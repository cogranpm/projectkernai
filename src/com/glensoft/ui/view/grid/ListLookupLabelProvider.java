package com.glensoft.ui.view.grid;

import org.eclipse.jface.viewers.LabelProvider;

import com.glensoft.data.entity.ListDetail;

public class ListLookupLabelProvider extends LabelProvider {
	
	@Override
	public String getText(Object element) {
		ListDetail listDetail = (ListDetail)element;
		return listDetail.getLabel();
	}

}
