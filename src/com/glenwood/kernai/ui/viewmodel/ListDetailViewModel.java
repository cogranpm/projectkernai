package com.glenwood.kernai.ui.viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.ui.abstraction.BaseMasterDetailViewModel;
import com.glenwood.kernai.ui.abstraction.BaseViewModel;

public class ListDetailViewModel extends BaseMasterDetailViewModel<ListDetail, ListHeader>{
	

	
	public ListDetailViewModel(ListHeader listHeader)
	{
		super(listHeader);
	}
	
	

}
