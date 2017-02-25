package com.glenwood.kernai.ui.abstraction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.glenwood.customExceptions.EntityInstantiationError;
import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.abstractions.IEntityRepository;

public class BaseEntityMasterDetailListEditPresenter<T extends BaseEntity, P extends BaseEntity> 
implements IEntityMasterDetailListEditPresenter<T, P> {

	protected IEntityRepository<T> repository;
	protected IEntityMasterDetailListEditView<T, P> view;
	protected IMasterDetailViewModel<T, P> model;
	private Class<T> clazz;
	private String entityTypeName;
	
	public BaseEntityMasterDetailListEditPresenter(IEntityMasterDetailListEditView<T, P> view, 
			IMasterDetailViewModel<T, P> model, Class entityClass, String entityTypeName) {
		super();
		this.view = view;
		this.model = model;
		this.clazz = entityClass;
		this.entityTypeName = entityTypeName;

	}
	
	@Override
	public void loadModels(P parent) {
		this.model.setParent(parent);
		
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
			this.model.setDirty(true);
			
			this.model.getCurrentItem().addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					modelChanged();
				}
			});
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			throw new EntityInstantiationError();
		}
		this.view.afterAdd();
	}



	@Override
	public void deleteModel() {
		this.model.getItems().remove(this.model.getCurrentItem());
		this.repository.delete(model.getCurrentItem());
		this.model.setCurrentItem(null);
		this.model.setDirty(false);
		this.view.refreshView();
	}

	@Override
	public void saveModel() {
		this.repository.save(this.model.getCurrentItem());
	}

	@Override
	public void loadModel(T entity) {
		model.setCurrentItem(entity);
		model.setDirty(false);
		this.model.getCurrentItem().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				modelChanged();
			}
		});
		this.view.refreshView();
		this.view.afterSelection();
	}

	
	@Override
	public void modelChanged() {
		this.model.setDirty(true);
	}
}
