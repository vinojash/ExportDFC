package com.export;

import java.io.FileReader;
import java.util.Properties;

import com.documentum.fc.common.DfLogger;

public class Target {

	public static void main(String[] args) throws Exception {

		FileReader reader = new FileReader("export.properties");

		Properties p = new Properties();
		p.load(reader);

		Query q = new Query();
		q.setAttributes(p.getProperty("queryAttribute"));
		q.setObjectType(p.getProperty("queryObjectType"));
		q.setWhereCondition(p.getProperty("queryWhereCondition"));
		q.setResultCount(p.getProperty("queryResultCount"));
		q.setDqlFullQuery("dqlFullQuery");

		DfLogger.info(Target.class, "Constructed query : {0}", new String[] { q.query() }, null);

		SessionDFC sessionDFC = new SessionDFC(p.getProperty("repositoryName"), p.getProperty("repositoryUserName"),
				p.getProperty("repositoryUserPassword"));
		BulkExport bulkExport = new BulkExport(sessionDFC);
		bulkExport.execute(q.query());

		sessionDFC.releaseSession();
		DfLogger.info(Target.class, "Export operation completed..!", new String[] {}, null);

	}
}
