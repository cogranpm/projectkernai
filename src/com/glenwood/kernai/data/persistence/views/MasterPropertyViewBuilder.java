package com.glenwood.kernai.data.persistence.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.couchbase.lite.Database;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.View;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.ListHeader;
import com.glenwood.kernai.data.entity.MasterPropertyListItem;
import com.glenwood.kernai.data.entity.MasterPropertyToMasterCategory;
import com.glenwood.kernai.data.persistence.MasterPropertyToMasterCategoryRepository;

public class MasterPropertyViewBuilder {
	
	public final static String QUERY_LISTHEADER_BY_NAME = "QUERYLISTHEADERBYNAME";

	
	public static void buildViews(Database database)
	{
		View listDetailByListHeaderView = database.getView(ListDetail.TYPE_NAME);
		listDetailByListHeaderView.setMap(new Mapper(){
			@Override
			public void map(Map<String, Object> document, Emitter emitter)
			{
				if(document.containsKey("type") && ListDetail.TYPE_NAME.equals(document.get("type")))
				{
					emitter.emit(document.get("listHeaderId"), null);
				}
			}
		}, "1");
		
		View masterPropertyListItemByMasterPropertyView = database.getView(MasterPropertyListItem.TYPE_NAME);
		masterPropertyListItemByMasterPropertyView.setMap(new Mapper(){
			@Override
			public void map(Map<String, Object> document, Emitter emitter)
			{
				if(document.containsKey("type") && MasterPropertyListItem.TYPE_NAME.equals(document.get("type")))
				{
					emitter.emit(document.get("masterPropertyId"), null);
				}
			}
		}, "1");
	
		View masterPropertyToCategoryView = database.getView(MasterPropertyToMasterCategory.TYPE_NAME);
		masterPropertyToCategoryView.setMap(new Mapper() {
			@Override
			public void map(Map<String, Object> document, Emitter emitter) {
				if(document.containsKey("type") && MasterPropertyToMasterCategory.TYPE_NAME.equals(document.get("type")))
				{
					emitter.emit(document.get("masterPropertyId"), null);
				}	
			}
			
		}, "1");
		
		View masterPropertyToCategoryGetView = database.getView(MasterPropertyToMasterCategoryRepository.GET_BY_PROPERTY_AND_CATEGORY_VIEWNAME);
		masterPropertyToCategoryGetView.setMap(new Mapper() {
			@Override
			public void map(Map<String, Object> document, Emitter emitter) {
				if(document.containsKey("type") && MasterPropertyToMasterCategory.TYPE_NAME.equals(document.get("type")))
				{
					List<Object> keys = new ArrayList<Object>();
		            keys.add(document.get("masterPropertyId"));
		            keys.add(document.get("masterCategoryId"));
		            emitter.emit(keys, null);
				}	
			}
		}, "1");
		
		View listHeaderByNameView = database.getView(QUERY_LISTHEADER_BY_NAME);
		listHeaderByNameView.setMap(new Mapper(){
			@Override
			public void map(Map<String, Object> document, Emitter emitter)
			{
				if(document.containsKey("type") && ListHeader.TYPE_NAME.equals(document.get("type")))
				{
					emitter.emit(document.get("name"), null);
				}
			}
		}, "1");
		


	}

}
