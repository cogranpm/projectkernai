package com.glenwood.kernai.data.mapping;

import java.util.Map;

import com.couchbase.lite.Document;
import com.glenwood.kernai.data.abstractions.ICouchBaseEntityMapper;
import com.glenwood.kernai.data.entity.Template;


public class TemplateCBaseMapper implements ICouchBaseEntityMapper<Template> {

	@Override
	public Template MapToEntity(Document document) {
		Template template = new Template();
		 Map<String, Object> map = document.getProperties();
		 if(map.containsKey("id"))
		 {
			 template.setId(map.get("id").toString());
		 }
		 if(map.containsKey("engine"))
		 {
			 template.setEngine(map.get("engine").toString());
		 }
		 if(map.containsKey("name"))
		 {
			 template.setName(map.getOrDefault("name", "").toString());
		 }
		 if(map.containsKey("bodyId"))
		 {
			 template.setBodyId(map.getOrDefault("bodyId", "").toString());
		 }
		return template;
	}

	@Override
	public  Map<String, Object> MapFromEntity(Template entity) {
		Map<String, Object> map = EntityMapFactory.getMap();
		map.put("name", entity.getName());
		map.put("engine", entity.getEngine());
		map.put("bodyId", entity.getBodyId());
		map.put("id", entity.getId());
		return map;
	}

}
