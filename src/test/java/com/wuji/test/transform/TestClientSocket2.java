package com.wuji.test.transform;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wuji.mina.transform.HLenProtocolCodecFactory;

public class TestClientSocket2 {
	private static Logger log = LoggerFactory.getLogger(TestClientSocket2.class);

	public static void main(String[] args) throws IOException, InterruptedException {
		for (int j = 10; j < 11; j++) {
			new Thread(new SocketClient(), "thread" + j).start();
		}

	}

	static class SocketClient implements Runnable {

		public void run() {
			IoConnector connector = new NioSocketConnector();
			IoSessionConfig config = connector.getSessionConfig();
			config.setUseReadOperation(Boolean.TRUE);
			connector.setHandler(new TestClientHandler());
			// config.setIdleTime(IdleStatus.BOTH_IDLE, 70);
			connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new HLenProtocolCodecFactory()));
			ConnectFuture connectFuture = connector.connect(new InetSocketAddress("127.0.0.1", 8081));
			try {
				connectFuture = connectFuture.await();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			IoSession session = connectFuture.getSession();
			String mqMsgs1 = Thread.currentThread().getName() + "hello word";
			mqMsgs1 = String.format("%06d", mqMsgs1.length()) + mqMsgs1;
			String mqMsgs = "thread11" + "hello word";
			mqMsgs = String.format("%06d", mqMsgs.length()) + mqMsgs;
			String m = mqMsgs + mqMsgs1;
			log.info("send msg:{}", m);
			session.write(m.getBytes());
			ReadFuture readFuture = session.read();
			ReadFuture readFuture1 = session.read();
			try {
				readFuture.await(60000);
				readFuture1.await(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Object message = readFuture.getMessage();
			String msg = new String((byte[]) message);
			log.info(Thread.currentThread().getName() + "   receive:" + msg.substring(6, msg.length()));
			Object message1 = readFuture1.getMessage();
			String msg1 = new String((byte[]) message1);
			log.info(Thread.currentThread().getName() + "   receive:" + msg1.substring(6, msg.length()));

			session.closeOnFlush();
			connector.dispose();
		}

	}
}
