package com.glenwood.kernai.data.abstractions;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseEntity implements IBaseEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pkey;
	
	
	@Transient
	@JsonProperty(value = "_id")
	protected String id;

	@JsonProperty
	protected String type;
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id) {
		String oldId = this.id;
		this.id = id;
		firePropertyChange("id", oldId, this.id);
	}
	
	@Override
	public String getType() {
		return type;
	}

	
	
	protected PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}
	
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		changeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		changeSupport.removePropertyChangeListener(listener);
	}
	
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue)
	{
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
	
	
	
}
