package com.glenwood.kernai.ui.abstraction;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import com.glenwood.kernai.data.abstractions.BaseEntity;

public abstract class BaseMasterDetailViewModel <T extends BaseEntity, P extends BaseEntity> implements IMasterDetailViewModel<T, P>{

	@SuppressWarnings("unused")
	private BaseMasterDetailViewModel()
	{
		
	}
	
	public BaseMasterDetailViewModel(P parent)
	{
		this.parent = parent;
	}
	
	private P parent;

	public void setParent(P parent)
	{
		P oldValue = this.parent;
		this.parent = parent;
		firePropertyChange("parent", oldValue, this.parent);
	}
	
	public P getParent()
	{
		return this.parent;
	}
	
	
	protected List<T> items;
	protected T currentItem;
	protected Boolean dirty;
	
	
	@Override
	public List<T> getItems() {
		return items;
	}
	
	@Override
	public void setItems(List<T> items) {
		this.items = items;
		firePropertyChange("items", null, null);
	}
	
	@Override
	public T getCurrentItem() {
		return currentItem;
	}
	
	@Override
	public void setCurrentItem(T currentItem) {
		T oldValue = this.currentItem;
		this.currentItem = currentItem;
		firePropertyChange("currentItem", oldValue, this.currentItem);
	}
	
	@Override
	public Boolean getDirty()
	{
		return this.dirty;
	}
	
	@Override
	public void setDirty(Boolean dirty)
	{
		Boolean oldValue = this.dirty;
		this.dirty = dirty;
		firePropertyChange("dirty", oldValue, this.dirty);
	}
	

	protected PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	
	@Override
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		changeSupport.addPropertyChangeListener(listener);
	}
	
	@Override
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		changeSupport.removePropertyChangeListener(listener);
	}
	
	@Override
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue)
	{
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

}
