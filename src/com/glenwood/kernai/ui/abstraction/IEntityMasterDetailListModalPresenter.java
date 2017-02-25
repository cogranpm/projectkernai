package com.glenwood.kernai.ui.abstraction;

import com.glenwood.kernai.data.abstractions.BaseEntity;


/**
 * @param <T> the type of the entity
 * @param <P> the parent type of the entity
 */
public interface IEntityMasterDetailListModalPresenter<T extends BaseEntity, P extends BaseEntity> {

	public void loadItems();
	public void loadItems(P parent);
	public void addModel();
	public void editModel(T entity);
	public void deleteModel(T entity);
	public void saveModel();
	
}
