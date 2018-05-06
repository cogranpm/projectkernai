package com.glenwood.kernai.data.persistence;

import java.util.List;

import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.Template;

public class TemplateRepository extends BaseRepository<Template>  implements IEntityRepository<Template> {

	private final ListDetailRepository listDetailRepository;
	
	public TemplateRepository(IPersistenceManager manager) {
		super(manager);
		this.listDetailRepository = new ListDetailRepository(manager);
	}

	
	
	@Override
	public List<Template> getAll(String type, Class<Template> aClass)
	{
		List<Template> templates = super.getAll(type, aClass);
		templates.forEach(x -> {
			if(x.getEngine() != null)
			{
				x.setEngineLookup(this.listDetailRepository.get(x.getEngine(), ListDetail.class));
			}
		});
		return templates;
	}

	


	@Override
	public Template get(String id, Class<Template> aClass) {
		Template template = super.get(id, aClass);
		if(template.getEngine() != null)
		{
			template.setEngineLookup(this.listDetailRepository.get(template.getEngine(), ListDetail.class));
		}
		return template;
	}
	
	@Override
	public void save(Template entity) {
	
		if(entity.getEngineLookup() != null)
		{
			entity.setEngine(entity.getEngineLookup().getId());
		}
		else
		{
			entity.setEngine(null);
		}
		super.save(entity);
	}
}
