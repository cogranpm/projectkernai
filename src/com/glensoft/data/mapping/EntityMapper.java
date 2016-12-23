package com.glensoft.data.mapping;

import java.util.HashMap;
import java.util.Map;

import com.couchbase.lite.Document;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glensoft.data.abstractions.BaseEntity;

public class EntityMapper {
	
	public Map<String, Object> toMap(BaseEntity entity)
	{
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = EntityMapFactory.getMap();
		map = mapper.convertValue(entity, Map.class);
		return map;
	}
	
	
	 public <T> T toEntity(Document document, Class<T> aClass) 
	 {
		 ObjectMapper m = new ObjectMapper();
	     return m.convertValue(document.getProperties(), aClass);
	 }

}
