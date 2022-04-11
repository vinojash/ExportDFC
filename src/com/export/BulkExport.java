package com.export;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfLogger;

public class BulkExport {
	private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

	private String excelPath = "Export/metadata/Report " + formatter.format(new Date()) + ".xlsx";
	private XSSFWorkbook workbook = new XSSFWorkbook();
	private XSSFSheet sheet = workbook.createSheet("Metadata");
	private SessionDFC sessionDFC = null;

	public BulkExport(SessionDFC sessionDFC) {
		this.sessionDFC = sessionDFC;
	}

	public void execute(String query) {
		try {
			DfQuery dfQuery = new DfQuery();
			IDfCollection coll = null;
			dfQuery.setDQL(query);
			coll = dfQuery.execute(this.sessionDFC.getSession(), IDfQuery.DF_READ_QUERY);
			Boolean insertColumnName = true;
			while (coll.next()) {
				new ContentDFC(sessionDFC).export("Export/content", coll);
				this.sheet = new MetaDataDFC(sessionDFC).export("Export/metadata", coll, sheet, insertColumnName);
				insertColumnName = false;
			}

			if (null != coll) {
				coll.close();
			}
		} catch (DfException e) {
			e.printStackTrace();
		} finally {
			try {
				FileOutputStream outputStream = new FileOutputStream(this.excelPath);
				this.workbook.write(outputStream);
				this.workbook.close();
				DfLogger.info(BulkExport.class, "Excel report saved at {0}", new String[] { this.excelPath }, null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
