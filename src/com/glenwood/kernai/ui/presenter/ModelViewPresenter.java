package com.glenwood.kernai.ui.presenter;

import com.glenwood.kernai.data.entity.Model;
import com.glenwood.kernai.ui.abstraction.IModelView;
import com.glenwood.kernai.ui.viewmodel.ModelViewModel;

public class ModelViewPresenter {
	
	IModelView view;
	ModelViewModel model;
	
	
	
	public IModelView getView() {
		return view;
	}

	public void setView(IModelView view) {
		this.view = view;
	}

	public ModelViewModel getModel() {
		return model;
	}

	public void setModel(ModelViewModel model) {
		this.model = model;
	}

	public ModelViewPresenter(IModelView view, ModelViewModel model)
	{
		this.view = view;
		this.model = model;

	}
	
	public void loadModels(String projectId)
	{
		/* model.currentModel = ??*/
		Model currentModel = new Model();
		currentModel.setName("Glensoft");
		this.model.setCurrentModel(currentModel);
	}

}
