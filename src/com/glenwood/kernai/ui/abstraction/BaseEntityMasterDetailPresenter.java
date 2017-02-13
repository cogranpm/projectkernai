package com.glenwood.kernai.ui.abstraction;

import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.abstractions.IEntityRepository;

public abstract class BaseEntityMasterDetailPresenter<T extends BaseEntity, P extends BaseEntity> implements IEntityMasterDetailPresenter<T, P> {

	protected IEntityRepository<T> repository;
	protected IEntityView view;
	protected IViewModel<T> model;
	
	@Override
	public void loadItems() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadItems(P parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addModel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editModel(T entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteModel(T entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveModel() {
		// TODO Auto-generated method stub
		
	}

}
