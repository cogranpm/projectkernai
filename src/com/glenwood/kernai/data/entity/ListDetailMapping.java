package com.glenwood.kernai.data.entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;


@JsonIgnoreProperties(ignoreUnknown=true)
public class ListDetailMapping extends BaseEntity {
	
	public static final String TYPE_NAME = "LISTDETAILMAPPING";
	
	private ListDetail fromDetail;
	private String fromDetailId;
	private ListDetail toDetail;
	private String toDetailId;
	private String ListHeaderMappingId;
	
	
	
	@JsonIgnore
	public ListDetail getFromDetail() {
		return fromDetail;
	}
	public void setFromDetail(ListDetail fromDetail) {
		ListDetail oldValue = this.fromDetail;
		this.fromDetail = fromDetail;
		firePropertyChange("fromDetail", oldValue, this.fromDetail);
	}
	@JsonProperty
	public String getFromDetailId() {
		return fromDetailId;
	}
	public void setFromDetailId(String fromDetailId) {
		String oldValue = this.fromDetailId;
		this.fromDetailId = fromDetailId;
		firePropertyChange("fromDetailId", oldValue, this.fromDetailId);
	}
	@JsonIgnore
	public ListDetail getToDetail() {
		return toDetail;
	}
	public void setToDetail(ListDetail toDetail) {
		ListDetail oldValue = this.toDetail;
		this.toDetail = toDetail;
		firePropertyChange("toDetail", oldValue, this.toDetail);
	}
	@JsonProperty
	public String getToDetailId() {
		return toDetailId;
	}
	public void setToDetailId(String toDetailId) {
		String oldValue = this.toDetailId;
		this.toDetailId = toDetailId;
		firePropertyChange("toDetailId", oldValue, this.toDetailId);
	}
	@JsonProperty
	public String getListHeaderMappingId() {
		return ListHeaderMappingId;
	}
	public void setListHeaderMappingId(String listHeaderMappingId) {
		ListHeaderMappingId = listHeaderMappingId;
	}
	public ListDetailMapping(ListHeaderMapping parent)
	{
		this();
		if(parent != null)
		{
			this.ListHeaderMappingId = parent.getId();
		}
	}
	public ListDetailMapping()
	{
		super();
		this.type = TYPE_NAME;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.toDetailId, this.fromDetailId, this.ListHeaderMappingId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListDetailMapping other = (ListDetailMapping) obj;
                
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!(this.id == other.id))
			return false;

		return true;
	}
	

}
