package com.wuji.test.transform;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestServerHandler extends IoHandlerAdapter {
	private static Logger log = LoggerFactory.getLogger(TestServerHandler.class);

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		byte[] msgs = (byte[]) message;
		String address = session.getLocalAddress().toString();
		String string = new String(msgs);
		log.info(address + "server receive msg:" + string);
		SocketChannel channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8082));
		String msg = string.substring(6, 14) + "hello server" + session.getId();
		msg = String.format("%06d", msg.length()) + msg;
		log.info(address + "send msg:" + msg);
		ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
		channel.write(buffer);
		channel.close();
		session.closeNow();
	}

}
