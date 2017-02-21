package com.glenwood.kernai.ui.viewmodel;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.ui.abstraction.BaseMasterDetailViewModel;

public class ListDetailViewModel extends BaseMasterDetailViewModel<ListDetail, ListHeader>{
	

	
	public ListDetailViewModel(ListHeader listHeader)
	{
		super(listHeader);
	}
	
	

}
