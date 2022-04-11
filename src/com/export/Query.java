package com.export;

public class Query {
	private String objectType;
	private String attributes;
	private String whereCondition;
	private String resultCount;

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public String getWhereCondition() {
		return whereCondition;
	}

	public void setWhereCondition(String whereCondition) {
		this.whereCondition = whereCondition;
	}

	public String getResultCount() {
		return resultCount;
	}

	public void setResultCount(String resultCount) {
		this.resultCount = resultCount;
	}

	public String query() {
		String query = "SELECT ";
		if (attributes != null && attributes != "*" && !attributes.isEmpty()) {
			query = query + attributes + " ";
		} else {
			query = query + "* ";
		}
		if (!this.objectType.isEmpty()) {
			query = query + "FROM " + this.objectType;
		}
		if (!this.whereCondition.isEmpty()) {
			query = query + " WHERE " + this.whereCondition;
		}
		if (!this.resultCount.isEmpty()) {
			query = query + " ENABLE(RETURN_TOP " + this.resultCount + ")";
		}
		query = query + ";";
		return query;
	}
}
