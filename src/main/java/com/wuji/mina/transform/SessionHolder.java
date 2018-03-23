package com.wuji.mina.transform;

import org.apache.mina.core.session.IoSession;

public class SessionHolder {
	private static ThreadLocal<IoSession> client_session = new ThreadLocal<IoSession>();
	private static ThreadLocal<IoSession> server_session = new ThreadLocal<IoSession>();

	public static IoSession getClientSession() {
		return client_session.get();
	}

	public static void setClientSession(IoSession clientSession) {
		client_session.set(clientSession);
	}

	public static void removeClientSession() {
		client_session.remove();
	}

	public static IoSession getServerSession() {
		return server_session.get();
	}

	public static void setServerSession(IoSession serverSession) {
		server_session.set(serverSession);
	}

	public static void removeServerSession() {
		server_session.remove();
	}

}
