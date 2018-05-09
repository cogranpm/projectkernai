package com.glenwood.kernai.ui.abstraction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.glenwood.customExceptions.EntityInstantiationError;
import com.glenwood.kernai.data.abstractions.BaseEntity;
import com.glenwood.kernai.data.abstractions.IEntityRepository;

public class BaseEntityPresenter<T extends BaseEntity> implements IEntityPresenter<T> {
	
	protected IEntityRepository<T> repository;
	protected IEntityView view;
	protected IViewModel<T> model;
	private Class<T> clazz;
	private String entityTypeName;
	
	/**
	 * blah
	 * <p>
	 * blah
	 * blah
	 *
	 * @param  entityTypeName  a string that uniquely identifies the domain entity
	 * @param  
	 * @return      
	 * @see         
	 */
	public BaseEntityPresenter(IEntityView view, IViewModel<T> model, Class entityClass, String entityTypeName) {
		super();
		this.view = view;
		this.model = model;
		this.clazz = entityClass;
		this.entityTypeName = entityTypeName;
	}

	@Override
	public void loadModel(T item) {
		model.setCurrentItem(item);
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
	public void loadModels() {
		this.model.setItems(this.repository.getAll(this.entityTypeName, this.clazz));
		if (this.model.getItems() != null && this.model.getItems().size() > 0)
		{
			this.model.setCurrentItem(this.model.getItems().get(0));
		}
	}

	@Override
	public void addModel() {
		try {
			this.model.setCurrentItem(this.clazz.newInstance());
			model.setDirty(true);
			this.model.getCurrentItem().addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					modelChanged();
				}
			});
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new EntityInstantiationError(e);
		}
		this.view.afterAdd();

	}

	@Override
	public void saveModel() {
		if(!model.getDirty()) { return; }
		this.repository.save(model.getCurrentItem());
		this.model.setDirty(false);
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
	public void modelChanged() {
		this.model.setDirty(true);
	}

}
