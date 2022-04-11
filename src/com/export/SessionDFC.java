package com.export;

import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.common.DfLogger;
import com.documentum.fc.common.IDfLoginInfo;

public class SessionDFC {

	public SessionDFC(String repo, String user, String pass) throws Exception {
		this.getSession(repo, user, pass);
	}

	private IDfClientX clientx = null;
	private IDfClient client = null;
	private IDfSessionManager sMgr = null;
	private IDfSession mySession = null;

	public IDfClientX getIDfClientX() {
		return this.clientx;
	}

	public IDfClient getIDfClient() {
		return this.client;
	}

	public IDfSessionManager getIDfSessionManager() {
		return this.sMgr;
	}

	private void getSession(String repo, String user, String pass) throws Exception {
		this.clientx = new DfClientX();
		this.client = clientx.getLocalClient();
		this.sMgr = client.newSessionManager();
		IDfLoginInfo loginInfoObj = clientx.getLoginInfo();
		loginInfoObj.setUser(user);
		loginInfoObj.setPassword(pass);
		loginInfoObj.setDomain(null);
		sMgr.setIdentity(repo, loginInfoObj);
		this.mySession = sMgr.getSession(repo);
		if (this.mySession != null) {
			DfLogger.info(Target.class, "Session created..!", new String[] {}, null);
		} else {
			DfLogger.error(SessionDFC.class, "Session not created..!", null, null);
		}
	}

	public IDfSession getSession() {
		return this.mySession;
	}

	public void releaseSession() throws Exception {
		if (this.mySession != null) {
			this.sMgr.release(this.mySession);
			DfLogger.info(Target.class, "Session released..!", new String[] {}, null);

		}
	}

}
