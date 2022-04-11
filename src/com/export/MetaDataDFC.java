package com.export;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfLogger;

public class MetaDataDFC {

	private SessionDFC sessionDFC = null;
	private String myDestination = null;
	private IDfCollection myColl = null;
	private XSSFSheet sheet = null;

	public MetaDataDFC(SessionDFC sessionDFC) {
		this.setSessionDFC(sessionDFC);
	}

	public XSSFSheet export(String myDestination, IDfCollection coll, XSSFSheet sheet, Boolean insertColumnName)
			throws DfException {
		this.myDestination = myDestination;
		this.myColl = coll;
		this.sheet = sheet;
		if (insertColumnName)
			this.insertRowTitle();
		this.insertRowValues();

		return this.sheet;
	}

	private void insertRowTitle() throws DfException {
		Row row = this.sheet.createRow(0);
		for (int i = 0; i < myColl.getAttrCount(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(myColl.getAttr(i).getName());
		}
		DfLogger.info(MetaDataDFC.class, "Row Title inserted in excel file..!", new String[] {}, null);
	}

	private void insertRowValues() throws DfException {

		Row row = this.sheet.createRow(this.sheet.getLastRowNum() + 1);
		for (int i = 0; i < myColl.getAttrCount(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(myColl.getString(myColl.getAttr(i).getName()));
		}
		DfLogger.info(MetaDataDFC.class, "New Row value inserted for the object id {0}", new String[] {myColl.getString("r_object_id")}, null);

	}

	public SessionDFC getSessionDFC() {
		return sessionDFC;
	}

	public void setSessionDFC(SessionDFC sessionDFC) {
		this.sessionDFC = sessionDFC;
	}

	public String getMyDestination() {
		return myDestination;
	}

	public void setMyDestination(String myDestination) {
		this.myDestination = myDestination;
	}

	public IDfCollection getMyColl() {
		return myColl;
	}

	public void setMyColl(IDfCollection myColl) {
		this.myColl = myColl;
	}

}
