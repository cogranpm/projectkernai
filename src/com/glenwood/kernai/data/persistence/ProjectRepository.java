package com.glenwood.kernai.data.persistence;

import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.Project;

public class ProjectRepository extends BaseRepository<Project> {

	public ProjectRepository(IPersistenceManager manager) {
		super(manager);
		
	}
	
	@Override
	public void delete(Project entity) {
		super.delete(entity);
		
		/* delete all child associations 
		 * model
		 * 
		 */
	}

}
