/* represents a large text document such as a template or a script */
package com.glenwood.kernai.data.entity;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.glenwood.kernai.data.abstractions.BaseEntity;

@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
public class SourceDocument extends BaseEntity  {
	public static final String TYPE_NAME = "SOURCEDOCUMENT";

	private String body;

	@JsonProperty
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		String oldBody = this.body;
		this.body = body;
		this.firePropertyChange("body", oldBody, this.body);
	}
}
