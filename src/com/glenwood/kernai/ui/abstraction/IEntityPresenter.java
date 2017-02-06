package com.glenwood.kernai.ui.abstraction;

import com.glenwood.kernai.data.abstractions.BaseEntity;


public interface IEntityPresenter<T> {
	
	public void loadModel(T item);
	public void loadModels();
	public void addModel();
	public void saveModel();
	public void deleteModel();

}
