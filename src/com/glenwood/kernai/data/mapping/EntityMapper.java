package com.glenwood.kernai.data.mapping;

import java.util.Map;

import com.couchbase.lite.Document;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glenwood.kernai.data.abstractions.BaseEntity;

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
		 Map<String, Object> map = document.getProperties();
		 /*
		 if(aClass == MasterProperty.class)
		 {
			 System.out.println(map.toString());
		 }
		 */
	     return m.convertValue(map, aClass);
	 }

}
