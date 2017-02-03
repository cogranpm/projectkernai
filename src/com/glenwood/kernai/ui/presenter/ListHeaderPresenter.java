package com.glenwood.kernai.ui.presenter;

import java.util.ArrayList;

import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.data.persistence.ListDetailRepository;
import com.glenwood.kernai.data.persistence.ListheaderRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.view.ListHeaderView;
import com.glenwood.kernai.ui.viewmodel.ListHeaderViewModel;

public class ListHeaderPresenter {
	
	private ListHeaderView view;
	private ListHeaderViewModel model;
	private ListheaderRepository repository;
	//private ListDetailRepository childRepository;
	
	public ListHeaderPresenter(ListHeaderView view, ListHeaderViewModel model)
	{
		this.view = view;
		this.model = model;
		this.repository = new ListheaderRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
		//this.childRepository = new ListDetailRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
	}
	
	public void loadModel(ListHeader item)
	{
		model.setCurrentItem(item);
		model.setDirty(false);
		this.view.refreshView();
	}
	
	public void loadModels()
	{
		this.model.setItems(this.repository.getAll(ListHeader.TYPE_NAME, ListHeader.class));
		if (this.model.getItems() != null && this.model.getItems().size() > 0)
		{
			this.model.setCurrentItem(this.model.getItems().get(0));
		}
		this.model.setChildItems(new ArrayList<ListDetail>());
	}
	
	/* to do - remove this */
	public void loadChildItems()
	{
		//this.model.setChildItems(this.childRepository.getAllByListHeader(this.model.getCurrentItem().getId()));
		//this.view.refreshChildView();
	}
	
	/* quick and dirty test here */
	public void saveChildItem(ListDetail entity)
	{
		//this.childRepository.save(entity);
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
		System.out.println("just deleted the model");
		this.model.getItems().remove(this.model.getCurrentItem());
		this.repository.delete(model.getCurrentItem());
		this.model.setCurrentItem(null);
		this.view.refreshView();
	}
	
	

}
