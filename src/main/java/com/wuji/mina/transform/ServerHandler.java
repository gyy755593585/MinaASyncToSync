package com.wuji.mina.transform;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler extends IoHandlerAdapter {

	private static Map<String, Long> sessionMapperMap = new ConcurrentHashMap<String, Long>();

	private static Logger log = LoggerFactory.getLogger(ServerHandler.class);

	@Override
	public void inputClosed(IoSession session) throws Exception {
		super.inputClosed(session);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		byte[] msgs = (byte[]) message;
		String address = session.getLocalAddress().toString();
		Map<Long, IoSession> sessions = session.getService().getManagedSessions();
		if (address.lastIndexOf("8081") > 0) {
			String msg = new String(msgs);
			log.info("client send msg:" + msg);
			sessionMapperMap.put(msg.substring(6, 14), session.getId());
			SocketChannel channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9091));
			channel.write(ByteBuffer.wrap(msgs));

		} else if (address.lastIndexOf("8082") > 0) {
			String msg = new String(msgs);
			log.info(address + "server receive msg:" + msg);
			IoSession clientSession = sessions.get(sessionMapperMap.get(msg.substring(6, 14)));
			clientSession.write(msgs);
			sessionMapperMap.remove(msg.substring(6, 14));
			if (!sessionMapperMap.containsValue(clientSession.getId())) {
				clientSession.closeOnFlush();
			}
			session.closeOnFlush();
		}
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.getMessage();
	}

}
