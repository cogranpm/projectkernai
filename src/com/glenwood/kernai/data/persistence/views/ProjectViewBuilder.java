package com.glenwood.kernai.data.persistence.views;

import java.util.Map;

import com.couchbase.lite.Database;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.View;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.Model;

public class ProjectViewBuilder {
	
	
	public final static String QUERY_MODEL_BY_PROJECT = "QUERYMODELBYPROJECT";
	
	public static void BuildViews(Database database)
	{
		View modelByProjectView = database.getView(QUERY_MODEL_BY_PROJECT);
		modelByProjectView.setMap(new Mapper(){
			@Override
			public void map(Map<String, Object> document, Emitter emitter)
			{
				if(document.containsKey("type") && ListDetail.TYPE_NAME.equals(document.get("type")))
				{
					emitter.emit(document.get("projectId"), null);
				}
			}
		}, "1");
	}

}
