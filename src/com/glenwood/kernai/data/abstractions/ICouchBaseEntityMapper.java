package com.glenwood.kernai.data.abstractions;

import java.util.Map;

import com.couchbase.lite.Document;

public interface  ICouchBaseEntityMapper <T extends BaseEntity> {
	public  T MapToEntity(Document document);
	public 	Map<String, Object> MapFromEntity(T entity);
}
