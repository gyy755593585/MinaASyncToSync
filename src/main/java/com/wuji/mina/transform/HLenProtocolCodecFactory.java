package com.wuji.mina.transform;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class HLenProtocolCodecFactory implements ProtocolCodecFactory {

	private ProtocolDecoder decoder;

	private ProtocolEncoder encoder;

	public HLenProtocolCodecFactory() {
		this.decoder = new ByteArrayDecoder();
		this.encoder = new ByteArrayEncoder();
	}

	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return this.encoder;
	}

	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return this.decoder;
	}

}
