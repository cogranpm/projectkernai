package com.glenwood.kernai.data.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Script extends BaseEntity {

	
	public static final String TYPE_NAME = "SCRIPT";
	
	private String engine;
	private String name;
	private String body;
	
	
	@JsonProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		this.firePropertyChange("name", oldName, this.name);
	}

	@JsonProperty
	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		String oldEngine = this.engine;
		this.engine = engine;
		this.firePropertyChange("engine", oldEngine, this.engine);
	}


	@JsonProperty
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		String oldBody = this.body;
		this.body = body;
		this.firePropertyChange("body", oldBody, this.body);
	}
	
	
	private ListDetail engineLookup;
	
	@JsonIgnore
	public ListDetail getEngineLookup() {
		return engineLookup;
	}

	public void setEngineLookup(ListDetail engineLookup) {
		ListDetail oldValue = this.engineLookup;
		this.engineLookup = engineLookup;
		firePropertyChange("engineLookup", oldValue, this.engineLookup);
	}

	
	
	public Script()
	{
		this.type = TYPE_NAME;
	}
	

	@Override
	public String toString() {
		return String.format("Script[id=%s,Name=%s,Engine=%]", this.id, this.name, this.engine);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.engine);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Script other = (Script) obj;
                
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!(this.id == other.id))
			return false;

		return true;
	}
	
}
