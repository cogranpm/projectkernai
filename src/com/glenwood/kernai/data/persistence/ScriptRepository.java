package com.glenwood.kernai.data.persistence;

import java.util.List;

import com.glenwood.kernai.data.abstractions.IEntityRepository;
import com.glenwood.kernai.data.abstractions.IPersistenceManager;
import com.glenwood.kernai.data.entity.ListDetail;
import com.glenwood.kernai.data.entity.Script;

public class ScriptRepository  extends BaseRepository<Script>  implements IEntityRepository<Script> {

	private final ListDetailRepository listDetailRepository;
	
	public ScriptRepository(IPersistenceManager manager) {
		super(manager);
		this.listDetailRepository = new ListDetailRepository(manager);
	}

	
	
	@Override
	public List<Script> getAll(String type, Class<Script> aClass)
	{
		List<Script> scripts = super.getAll(type, aClass);
		scripts.forEach(x -> {
			if(x.getEngine() != null)
			{
				x.setEngineLookup(this.listDetailRepository.get(x.getEngine(), ListDetail.class));
			}
		});
		return scripts;
	}

	


	@Override
	public Script get(String id, Class<Script> aClass) {
		Script script = super.get(id, aClass);
		if(script.getEngine() != null)
		{
			script.setEngineLookup(this.listDetailRepository.get(script.getEngine(), ListDetail.class));
		}
		return script;
	}
	
	@Override
	public void save(Script entity) {
	
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
