package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.ListHeader;
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
	
	public void loadModels()
	{
		this.model.setItems(this.repository.getAll(ListHeader.TYPE_NAME, ListHeader.class));
		if (this.model.getItems() != null && this.model.getItems().size() > 0)
		{
			this.model.setCurrentItem(this.model.getItems().get(0));
		}
	}
	
	public void addModel()
	{
		ListHeader newListHeader= new ListHeader();
		model.setCurrentItem(newListHeader);
	}
	
	public void saveModel()
	{
		this.repository.save(model.getCurrentItem());
	}
	
	public void deleteModel()
	{
		this.repository.delete(model.getCurrentItem());
	}
	
	

}
