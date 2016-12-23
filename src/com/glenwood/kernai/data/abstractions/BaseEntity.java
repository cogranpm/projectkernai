package com.glenwood.kernai.data.abstractions;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseEntity {
	
	@JsonProperty(value = "_id")
	protected String id;

	@JsonProperty
	protected String type;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}

	
	
	protected PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	
	protected void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		changeSupport.addPropertyChangeListener(listener);
	}
	
	protected void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		changeSupport.removePropertyChangeListener(listener);
	}
	
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue)
	{
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
	
}
