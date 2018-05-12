package com.glenwood.kernai.data.persistence;

import java.util.List;

import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.SourceDocument;
import com.glenwood.kernai.data.entity.Template;

public class TemplateRepository extends BaseRepository<Template>  implements IEntityRepository<Template> {

	private final ListDetailRepository listDetailRepository;
	private final SourceDocumentRepository sourceDocumentRepository;
	
	public TemplateRepository(IPersistenceManager manager) {
		super(manager);
		this.listDetailRepository = new ListDetailRepository(manager);
		this.sourceDocumentRepository = new SourceDocumentRepository(manager);
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
	
		/* if the body is set on the entity save it using the SourceDocument repository */
		if(entity.getSourceDocument() != null && entity.getSourceDocument().getBody() != null && !entity.getSourceDocument().getBody().isEmpty())
		{
			this.sourceDocumentRepository.save(entity.getSourceDocument());
			entity.setBodyId(entity.getSourceDocument().getId());
		}
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
	
	@Override
	public void loadExtraFields(Template entity) {
		super.loadExtraFields(entity);
		if(entity.getBodyId() != null && !entity.getBodyId().isEmpty())
		{
			this.sourceDocumentRepository.get(entity.getBodyId(), SourceDocument.class);
		}
	
	}
}
