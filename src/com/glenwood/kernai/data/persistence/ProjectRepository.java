package com.glenwood.kernai.data.persistence;

import java.util.List;

import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.Model;
import com.glenwood.kernai.data.entity.Project;

public class ProjectRepository extends BaseRepository<Project> {
	
	ModelRepository modelRepository;

	public ProjectRepository(IPersistenceManager manager) {
		super(manager);
		this.modelRepository = new ModelRepository(manager);
	}
	
	@Override
	public void delete(Project entity) {
		
		
		/* delete all child associations 
		 * model
		 * 
		 */
		
		List<Model> models = this.modelRepository.getAllByProject(entity.getId());
		for(Model model: models)
		{
			this.modelRepository.delete(model);
		}
		
		super.delete(entity);
		

	}

}
