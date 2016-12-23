package com.glensoft.data.mapping;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glensoft.data.entity.Attribute;

public class AttributeMapper {
	
	public static Map<String, Object> toMap(Attribute attribute)
	{
		
		Map<String, Object> map = EntityMapFactory.getMap();
		ObjectMapper mapper = new ObjectMapper();
		map = mapper.convertValue(attribute, Map.class);
		/*
		map.put("name", attribute.getName());
		map.put("dataType", attribute.getDataType());
		map.put("length", attribute.getLength());
		map.put("allowNull", attribute.getAllowNull());
		map.put("type", "attribute");
		*/
		return map;
	}
	
	public static Attribute fromMap(Map<String, Object> map)
	{
		Attribute attribute = new Attribute();
		attribute.setName((String)map.get("name"));
		attribute.setLength((Long)map.get("length"));
		attribute.setDataType((String)map.get("dataType"));
		attribute.setAllowNull((Boolean)map.get("allowNull"));
		return attribute;
	}

}
