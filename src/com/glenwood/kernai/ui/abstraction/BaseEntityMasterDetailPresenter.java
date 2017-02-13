package com.glenwood.kernai.ui.abstraction;

import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.abstractions.IEntityRepository;

public abstract class BaseEntityMasterDetailPresenter<T extends BaseEntity, P extends BaseEntity> implements IEntityMasterDetailPresenter<T, P> {

	protected IEntityRepository<T> repository;
	protected IEntityMasterDetailView<T, P> view;
	protected IMasterDetailViewModel<T, P> model;
	
	public BaseEntityMasterDetailPresenter(IEntityMasterDetailView<T, P> view, IMasterDetailViewModel<T, P> model) {
		super();
		this.view = view;
		this.model = model;
	}

	
	@Override
	public void loadItems() {
		
	}

	@Override
	public void loadItems(P parent) {
		this.model.setParent(parent);
		this.loadItems();
	}

	@Override
	public void addModel() {
		
	}

	@Override
	public void editModel(T entity) {
		this.model.setCurrentItem(entity);
		this.view.showAddEdit(false);
	}

	@Override
	public void deleteModel(T entity) {
		if(entity != null)
		{
			repository.delete(entity);
			
			this.model.getItems().remove(entity);
			this.view.refreshView();
		}
		
	}

	@Override
	public void saveModel() {
		this.repository.save(this.model.getCurrentItem());
	}

}
