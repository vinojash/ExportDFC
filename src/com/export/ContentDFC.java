package com.export;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.DfLogger;
import com.documentum.fc.common.IDfId;
import com.documentum.operations.IDfExportNode;
import com.documentum.operations.IDfExportOperation;

public class ContentDFC {

	private String myDestination = null;
	private IDfSysObject myDctmObject = null;
	private SessionDFC sessionDFC = null;
	private IDfCollection myColl = null;
	private String tempPath = null;

	public ContentDFC(SessionDFC sessionDFC) {
		this.sessionDFC = sessionDFC;
	}

	public void setDctmObject(String objectId) throws DfException {
		this.myDctmObject = (IDfSysObject) sessionDFC.getSession().getObject(new DfId(objectId));
	}

	public void export(String myDestination, String objectId) throws DfException {
		this.myDestination = myDestination;
		this.setDctmObject(objectId);
		this.export();
	}

	public void export(String myDestination, IDfId objectId) throws DfException {
		this.myDestination = myDestination;
		this.myDctmObject = (IDfSysObject) sessionDFC.getSession().getObject(objectId);
		this.export();
	}

	private void export() throws DfException {
		IDfExportOperation operation = this.sessionDFC.getIDfClientX().getExportOperation();
		IDfExportNode node = (IDfExportNode) operation.add(myDctmObject);
		this.tempPath = this.myDestination + "/" + myDctmObject.getObjectId() + "."
				+ myDctmObject.getFormat().getName();
		node.setFilePath(this.tempPath);
		operation.execute();

		DfLogger.info(ContentDFC.class, "Exported File :  {0}", new String[] { this.tempPath }, null);
	}

	public void export(String myDestination, IDfCollection coll) throws DfException {
		this.myDestination = myDestination;
		this.setMyColl(coll);
		this.myDctmObject = (IDfSysObject) sessionDFC.getSession().getObject(new DfId(coll.getString("r_object_id")));
		this.export();

	}

	public IDfCollection getMyColl() {
		return myColl;
	}

	public void setMyColl(IDfCollection myColl) {
		this.myColl = myColl;
	}
}
