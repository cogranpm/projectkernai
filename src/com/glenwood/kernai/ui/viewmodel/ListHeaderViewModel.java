package com.glenwood.kernai.ui.viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.ui.abstraction.BaseViewModel;
import com.glenwood.kernai.ui.abstraction.IViewModel;

public class ListHeaderViewModel extends BaseViewModel<ListHeader> {
	

	private List<ListDetail> childItems;
	
	public List<ListDetail> getChildItems() {
		return childItems;
	}
	public void setChildItems(List<ListDetail> childItems) {
		this.childItems = childItems;
		firePropertyChange("childItems", null, null);
	}


	public ListHeaderViewModel()
	{
		super();
	}

}
