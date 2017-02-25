package com.glenwood.kernai.ui.abstraction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.glenwood.customExceptions.EntityInstantiationError;
import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.abstractions.IEntityRepository;

public abstract class BaseEntityMasterDetailPresenter<T extends BaseEntity, P extends BaseEntity> implements IEntityMasterDetailPresenter<T, P> {

	protected IEntityRepository<T> repository;
	protected IEntityMasterDetailView<T, P> view;
	protected IMasterDetailViewModel<T, P> model;
	private Class<T> clazz;
	private String entityTypeName;

	public BaseEntityMasterDetailPresenter(IEntityMasterDetailView<T, P> view, IMasterDetailViewModel<T, P> model, Class entityClass, String entityTypeName) {
		super();
		this.view = view;
		this.model = model;
		this.clazz = entityClass;
		this.entityTypeName = entityTypeName;

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
		Constructor[] ctors = this.clazz.getDeclaredConstructors();
		Constructor ctor = null;
		for (int i = 0; i < ctors.length; i++) {
		    ctor = ctors[i];
		    if (ctor.getGenericParameterTypes().length > 0)
			break;
		}
		try {
			this.model.setCurrentItem((T)ctor.newInstance(this.model.getParent()));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			throw new EntityInstantiationError();
		}
		this.view.showAddEdit(true);
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
			((IEntityView)this.view).refreshView();
		}
		
	}

	@Override
	public void saveModel() {
		this.repository.save(this.model.getCurrentItem());
	}

}
