package com.wuji.mina.transform;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * 编码器将数据直接发出去(不做处理)
 */
public class ByteArrayEncoder extends ProtocolEncoderAdapter {

	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		byte[] msgs = (byte[]) message;
		out.write(IoBuffer.wrap(msgs));
	}

}
