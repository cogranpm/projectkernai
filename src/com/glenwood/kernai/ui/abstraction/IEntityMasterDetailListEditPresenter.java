package com.glenwood.kernai.ui.abstraction;

import com.glenwood.kernai.data.abstractions.BaseEntity;

public interface IEntityMasterDetailListEditPresenter<T extends BaseEntity, P extends BaseEntity> {
	
	public void loadModels(P parent);
	public void addModel();
	public void deleteModel();
	public void saveModel();
	public void loadModel(T entity);
	public void modelChanged();
}
