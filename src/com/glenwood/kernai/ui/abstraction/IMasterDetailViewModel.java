package com.glenwood.kernai.ui.abstraction;

import java.beans.PropertyChangeListener;
import java.util.List;

import com.glenwood.kernai.data.abstractions.BaseEntity;

public interface IMasterDetailViewModel <T extends BaseEntity, P extends BaseEntity> {
	
	public List<T> getItems();
	public void setItems(List<T> items);
	public T getCurrentItem();
	public void setCurrentItem(T currentItem);
	public void setParent(P parent);
	public P getParent();
	public Boolean getDirty();
	public void setDirty(Boolean dirty);
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue);

}
