package com.glenwood.kernai.ui.abstraction;

public interface IEntityPresenter<T> {
	
	public void loadModel(T item);
	public void loadModels();
	public void addModel();
	public void saveModel();
	public void deleteModel();
	public void modelChanged();

}
