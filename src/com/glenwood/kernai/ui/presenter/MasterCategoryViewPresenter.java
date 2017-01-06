package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.MasterCategory;
import com.glenwood.kernai.data.persistence.MasterCategoryRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.view.MasterCategoryView;
import com.glenwood.kernai.ui.viewmodel.MasterCategoryViewModel;

public class MasterCategoryViewPresenter {
	
	private MasterCategoryView view;
	private MasterCategoryViewModel model; 
	private MasterCategoryRepository repository;
	
	public MasterCategoryViewPresenter(MasterCategoryView view, MasterCategoryViewModel model)
	{
		this.view = view;
		this.model = model;
		this.repository = new MasterCategoryRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));

	}

	
	
	public void loadModels()
	{
		this.model.setItems(this.repository.getAll(MasterCategory.TYPE_NAME, MasterCategory.class));
		if (this.model.getItems() != null && this.model.getItems().size() > 0)
		{
			this.model.setCurrentItem(this.model.getItems().get(0));
		}
		//this.view.refreshList();
	}
	
	public void addModel()
	{
		MasterCategory newMasterCategory = new MasterCategory();
		model.setCurrentItem(newMasterCategory);
		//model.getCurrentItem().setName("fred");
		//this.view.updateList();
		//this.view.refreshList();
	}
	
	public void saveModel()
	{
		//model.getCurrentItem().setName("Falcone");
		this.repository.save(model.getCurrentItem());
		//this.view.refreshList();
	}
	
	public void deleteModel()
	{
		this.repository.delete(model.getCurrentItem());
		//this.view.updateList();
	}
}
