package com.wuji.test.transform;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.wuji.mina.transform.HLenProtocolCodecFactory;

public class TestServerSocket {
	public static void main(String[] args) throws IOException {
		IoAcceptor acceptor = new NioSocketAcceptor();
		IoSessionConfig config = acceptor.getSessionConfig();
		config.setReadBufferSize(2048);
		acceptor.setHandler(new TestServerHandler());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new HLenProtocolCodecFactory()));
		acceptor.bind(new InetSocketAddress(9091));
	}
}
