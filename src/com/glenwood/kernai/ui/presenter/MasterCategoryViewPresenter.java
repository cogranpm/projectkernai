package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.MasterCategory;
import com.glenwood.kernai.data.persistence.MasterCategoryRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityPresenter;
import com.glenwood.kernai.ui.view.MasterCategoryView;
import com.glenwood.kernai.ui.viewmodel.MasterCategoryViewModel;

public class MasterCategoryViewPresenter extends BaseEntityPresenter<MasterCategory> {
	
	
	public MasterCategoryViewPresenter(MasterCategoryView view, MasterCategoryViewModel model)
	{
		super(view, model);
		this.repository = new MasterCategoryRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));

	}

	
	@Override
	public void loadModels()
	{
		this.model.setItems(this.repository.getAll(MasterCategory.TYPE_NAME, MasterCategory.class));
		if (this.model.getItems() != null && this.model.getItems().size() > 0)
		{
			this.model.setCurrentItem(this.model.getItems().get(0));
		}
	}

	@Override
	public void addModel()
	{
		MasterCategory newMasterCategory = new MasterCategory();
		model.setCurrentItem(newMasterCategory);
	}
	

}
