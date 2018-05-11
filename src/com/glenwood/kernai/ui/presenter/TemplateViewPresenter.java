package com.glenwood.kernai.ui.presenter;


import com.glenwood.kernai.data.entity.Template;
import com.glenwood.kernai.data.persistence.ListHeaderRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.data.persistence.TemplateRepository;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityPresenter;
import com.glenwood.kernai.ui.view.TemplateView;
import com.glenwood.kernai.ui.viewmodel.TemplateViewModel;


public class TemplateViewPresenter extends BaseEntityPresenter<Template>{

	
	ListHeaderRepository listHeaderRepository;
	
	public TemplateViewPresenter(TemplateView view, TemplateViewModel model)
	{
		super(view, model, Template.class, Template.TYPE_NAME);
		this.repository = new TemplateRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
		this.listHeaderRepository = new ListHeaderRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
		TemplateViewModel aModel = (TemplateViewModel)this.model;
		aModel.setEngineLookup(this.listHeaderRepository.getListItemsByName(ApplicationData.LIST_TEMPLATE_ENGINE_NAME));
	}
	
	@Override
	public void loadModel(Template item) {
		super.loadModel(item);
		TemplateViewModel aModel = (TemplateViewModel)this.model;
//		aModel.getDocument().set(item.getBody());

	}
	
	@Override
	public void saveModel() {

		TemplateViewModel aModel = (TemplateViewModel)this.model;
//		this.model.getCurrentItem().setBody(aModel.getDocument().get());
		super.saveModel();
	}
	

}
