package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.Script;
import com.glenwood.kernai.data.persistence.ListHeaderRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.data.persistence.ScriptRepository;
import com.glenwood.kernai.ui.ApplicationData;
import com.glenwood.kernai.ui.abstraction.BaseEntityPresenter;
import com.glenwood.kernai.ui.view.ScriptView;
import com.glenwood.kernai.ui.viewmodel.ScriptViewModel;

public class ScriptViewPresenter extends BaseEntityPresenter<Script>{

	
	ListHeaderRepository listHeaderRepository;
	
	public ScriptViewPresenter(ScriptView view, ScriptViewModel model)
	{
		super(view, model, Script.class, Script.TYPE_NAME);
		this.repository = new ScriptRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
		this.listHeaderRepository = new ListHeaderRepository(PersistenceManagerFactory.getPersistenceManager(ApplicationData.instance().getPersistenceType()));
		ScriptViewModel aModel = (ScriptViewModel)this.model;
		aModel.setEngineLookup(this.listHeaderRepository.getListItemsByName(ApplicationData.LIST_SCRIPT_ENGINE_NAME));
	}
	
	@Override
	public void loadModel(Script item) {
		super.loadModel(item);
		ScriptViewModel aModel = (ScriptViewModel)this.model;
//		aModel.getDocument().set(item.getBody());

	}
	
	@Override
	public void saveModel() {

		ScriptViewModel aModel = (ScriptViewModel)this.model;
//		this.model.getCurrentItem().setBody(aModel.getDocument().get());
		String script = aModel.getDocument().get();
		System.out.println(script);
		super.saveModel();
	}
	

}



