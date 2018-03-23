package com.wuji.mina.transform;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaTest {
	public static void main(String[] args) throws IOException {
		IoAcceptor acceptor = new NioSocketAcceptor();
		IoSessionConfig config = acceptor.getSessionConfig();
		// config.setIdleTime(IdleStatus.BOTH_IDLE, 70);
		config.setReadBufferSize(2048);
		acceptor.setHandler(new ServerHandler());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new HLenProtocolCodecFactory()));
		acceptor.bind(new InetSocketAddress(8081));
		acceptor.bind(new InetSocketAddress(8082));
	}
}
