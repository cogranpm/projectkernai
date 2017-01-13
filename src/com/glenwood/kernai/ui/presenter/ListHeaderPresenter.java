package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.persistence.ListheaderRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.view.ListHeaderView;
import com.glenwood.kernai.ui.viewmodel.ListHeaderViewModel;

public class ListHeaderPresenter {
	
	private ListHeaderView view;
	private ListHeaderViewModel model;
	private ListheaderRepository repository;
	
	public ListHeaderPresenter(ListHeaderView view, ListHeaderViewModel model)
	{
		this.view = view;
		this.model = model;
		this.repository = new ListheaderRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
	}
	
	

}
