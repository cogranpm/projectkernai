package com.glenwood.kernai.ui.viewmodel;


import java.util.ArrayList;
import java.util.List;

import com.glenwood.kernai.data.entity.DataConnection;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.ui.abstraction.BaseViewModel;

public class DataConnectionViewModel extends BaseViewModel<DataConnection> {

	private List<ListDetail> vendorNameLookup;
	
	public DataConnectionViewModel()
	{
		super();
		this.vendorNameLookup = new ArrayList<ListDetail>();
	}

	public List<ListDetail> getVendorNameLookup() {
		return vendorNameLookup;
	}

	public void setVendorNameLookup(List<ListDetail> vendorNameLookup) {
		this.vendorNameLookup = vendorNameLookup;
	}
	
	
}
