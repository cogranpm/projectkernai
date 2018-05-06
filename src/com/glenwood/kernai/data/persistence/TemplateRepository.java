package com.glenwood.kernai.data.persistence;

import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.Template;

public class TemplateRepository extends BaseRepository<Template>  implements IEntityRepository<Template> {

	public TemplateRepository(IPersistenceManager manager) {
		super(manager);
	}

}
