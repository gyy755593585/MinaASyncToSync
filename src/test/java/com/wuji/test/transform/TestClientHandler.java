package com.wuji.test.transform;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestClientHandler extends IoHandlerAdapter {
	private static Logger log = LoggerFactory.getLogger(TestClientHandler.class);

	@Override
	public void inputClosed(IoSession session) throws Exception {
		super.inputClosed(session);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		byte[] msgs = (byte[]) message;
		String address = session.getLocalAddress().toString();
		log.info(address + "client receive msg:" + new String(msgs));
		session.closeNow();

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		super.sessionIdle(session, status);
	}

}
