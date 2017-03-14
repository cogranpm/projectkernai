package com.glenwood.kernai.data.persistence.views;

import java.util.Map;

import com.couchbase.lite.Database;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.View;
import com.glenwood.kernai.data.entity.Association;
import com.glenwood.kernai.data.entity.Attribute;
import com.glenwood.kernai.data.entity.Entity;
import com.glenwood.kernai.data.entity.ImportTable;
import com.glenwood.kernai.data.entity.Model;

public class ProjectViewBuilder {
	
	
	public final static String QUERY_MODEL_BY_PROJECT = "QUERYMODELBYPROJECT";
	public final static String QUERY_ENTITY_BY_MODEL = "QUERYENTITYBYMODEL";
	public final static String QUERY_ASSOCIATION_BY_MODEL = "QUERYASSOCIATIONBYMODEL";
	public final static String QUERY_ATTRIBUTE_BY_ENTITY = "QUERYATTRIBUTEBYENTITY";
	public final static String QUERY_IMPORTTABLLE_BY_IMPORTDEFINITION = "QUERYIMPORTTABLEBYIMPORTDEFINITION";
	
	public static void buildViews(Database database)
	{
		View modelByProjectView = database.getView(QUERY_MODEL_BY_PROJECT);
		modelByProjectView.setMap(new Mapper(){
			@Override
			public void map(Map<String, Object> document, Emitter emitter)
			{
				if(document.containsKey("type") && Model.TYPE_NAME.equals(document.get("type")))
				{
					emitter.emit(document.get("projectId"), null);
				}
			}
		}, "1");
		
		
		View entityByModelView = database.getView(QUERY_ENTITY_BY_MODEL);
		entityByModelView.setMap(new Mapper(){
			@Override
			public void map(Map<String, Object> document, Emitter emitter)
			{
				if(document.containsKey("type") && Entity.TYPE_NAME.equals(document.get("type")))
				{
					emitter.emit(document.get("modelId"), null);
				}
			}
		}, "1");
		
		View associationByModelView = database.getView(QUERY_ASSOCIATION_BY_MODEL);
		associationByModelView.setMap(new Mapper(){
			@Override
			public void map(Map<String, Object> document, Emitter emitter)
			{
				if(document.containsKey("type") && Association.TYPE_NAME.equals(document.get("type")))
				{
					emitter.emit(document.get("modelId"), null);
				}
			}
		}, "1");
		
		View attributeByEntityView = database.getView(QUERY_ATTRIBUTE_BY_ENTITY);
		attributeByEntityView.setMap(new Mapper(){
			@Override
			public void map(Map<String, Object> document, Emitter emitter)
			{
				if(document.containsKey("type") && Attribute.TYPE_NAME.equals(document.get("type")))
				{
					emitter.emit(document.get("entityId"), null);
				}
			}
		}, "1");

	
	
		View importTableByImportDefinition = database.getView(QUERY_IMPORTTABLLE_BY_IMPORTDEFINITION);
		importTableByImportDefinition.setMap(new Mapper(){
			@Override
			public void map(Map<String, Object> document, Emitter emitter)
			{
				if(document.containsKey("type") && ImportTable.TYPE_NAME.equals(document.get("type")))
				{
					emitter.emit(document.get("importDefinitionId"), null);
				}
			}
		}, "1");
	}
}
