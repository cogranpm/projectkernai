package com.glensoft.ui.viewmodel;

import java.util.List;

import com.glensoft.data.entity.Model;

public class ModelViewModel {
	
	private List<Model> models;
	private Model currentModel;
	
	public List<Model> getModels() {
		return models;
	}
	public void setModels(List<Model> models) {
		this.models = models;
	}
	public Model getCurrentModel() {
		return currentModel;
	}
	public void setCurrentModel(Model currentModel) {
		this.currentModel = currentModel;
	}
	
	

}
