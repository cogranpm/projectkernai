package com.glenwood.kernai.ui.presenter;


import com.glenwood.kernai.data.entity.SourceDocument;
import com.glenwood.kernai.data.entity.Template;
import com.glenwood.kernai.data.persistence.ListHeaderRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.data.persistence.SourceDocumentRepository;
import com.glenwood.kernai.data.persistence.TemplateRepository;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityPresenter;
import com.glenwood.kernai.ui.view.TemplateView;
import com.glenwood.kernai.ui.viewmodel.TemplateViewModel;


public class TemplateViewPresenter extends BaseEntityPresenter<Template>{

	
	private ListHeaderRepository listHeaderRepository;
	private SourceDocumentRepository sourceDocumentRepository;
	private TemplateViewModel aModel;
	
	public TemplateViewPresenter(TemplateView view, TemplateViewModel model)
	{
		super(view, model, Template.class, Template.TYPE_NAME);
		this.repository = new TemplateRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
		this.listHeaderRepository = new ListHeaderRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
		this.sourceDocumentRepository = new SourceDocumentRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
		aModel = (TemplateViewModel)this.model;
		aModel.setEngineLookup(this.listHeaderRepository.getListItemsByName(ApplicationData.LIST_TEMPLATE_ENGINE_NAME));
	}
	
	@Override
	public void loadModel(Template item) {
		super.loadModel(item);
	}
	
	@Override
	public void saveModel() {

		/* extract the text from the SourceView document */
		if(this.model.getCurrentItem().getSourceDocument()!= null)
		{
			this.model.getCurrentItem().getSourceDocument().setBody(aModel.getDocument().get());
		}
		super.saveModel();
	}
	
	public void selectListItem()
	{
		this.repository.loadExtraFields(model.getCurrentItem());
		if(model.getCurrentItem().getSourceDocument() != null)
		{
			aModel.getDocument().set(model.getCurrentItem().getSourceDocument().getBody());
		}
	}
	

}
