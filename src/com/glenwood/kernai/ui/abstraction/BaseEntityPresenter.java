package com.glenwood.kernai.ui.abstraction;

import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.data.persistence.ListheaderRepository;
import com.glenwood.kernai.data.persistence.PersistenceManagerFactory;
import com.glenwood.kernai.ui.ApplicationData;

public class BaseEntityPresenter<T extends BaseEntity> implements IEntityPresenter<T> {
	
	protected IEntityRepository<T> repository;
	protected IEntityView view;
	protected IViewModel<T> model;
	
	public BaseEntityPresenter(IEntityView view, IViewModel<T> model) {
		super();
		this.view = view;
		this.model = model;
	}

	@Override
	public void loadModel(T item) {
		model.setCurrentItem(item);
		model.setDirty(false);
		this.view.refreshView();
	}

	@Override
	public void loadModels() {

	}

	@Override
	public void addModel() {


	}

	@Override
	public void saveModel() {
		this.repository.save(model.getCurrentItem());
		this.model.setDirty(false);
	}

	@Override
	public void deleteModel() {
		this.model.getItems().remove(this.model.getCurrentItem());
		this.repository.delete(model.getCurrentItem());
		this.model.setCurrentItem(null);
		this.view.refreshView();
	}

}
