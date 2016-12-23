package com.glensoft.data.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.glensoft.data.abstractions.BaseEntity;



public class Entity extends BaseEntity {
	
	public static final String TYPE_NAME = "ENTITY";
	
	@JsonProperty
	private String name;
	
	//private List<Attribute> attributes;
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	/*
	public List<Attribute> getAttributes()
	{
		return this.attributes;
	}
	
	public void addAttribute(Attribute attribute)
	{
		if(!this.attributes.contains(attribute))
		{
			
			this.attributes.add(attribute);
		}
	}
	
	public void removeAttribute(Attribute attribute)
	{
		if(this.attributes.contains(attribute))
		{
			this.attributes.remove(attribute);
		}
	}
	*/
	
	public Entity()
	{
		this.type = TYPE_NAME;
		//this.attributes = new ArrayList<Attribute>();
	}

}
