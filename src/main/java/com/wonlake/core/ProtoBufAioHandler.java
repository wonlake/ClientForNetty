package com.wonlake.core;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.AioHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2017/5/12.
 */
public abstract class ProtoBufAioHandler implements AioHandler<Object, ProtoBufPacket, Object> {
    private static Logger logger = LoggerFactory.getLogger(ProtoBufAioHandler.class);

    @Override
    public ByteBuffer encode(ProtoBufPacket packet,
                             GroupContext<Object, ProtoBufPacket, Object> groupContext,
                             ChannelContext<Object, ProtoBufPacket, Object> channelContext) {
        byte[] body = packet.getBody();
        int bodyLen = 0;
        if( body != null)
        {
            bodyLen = body.length;
        }
        int lengthSize = CodedOutputStream.computeSInt32SizeNoTag(bodyLen);
        int allLen = lengthSize + bodyLen;

        ByteBuffer buffer = ByteBuffer.allocate(allLen);
        buffer.order(groupContext.getByteOrder());
        CodedOutputStream s = CodedOutputStream.newInstance(buffer);
        try
        {
            s.writeInt32NoTag(bodyLen);
            s.writeRawBytes(body);
            s.flush();
        }
        catch (Exception ex)
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            logger.error(sw.toString());
        }
        return buffer;
    }

    @Override
    public ProtoBufPacket decode(ByteBuffer buffer,
                                 ChannelContext<Object, ProtoBufPacket, Object> channelContext) throws AioDecodeException {
        int readableLength = buffer.limit() - buffer.position();
        if(readableLength < 4)
        {
            return null;
        }

        CodedInputStream s = CodedInputStream.newInstance(buffer);
        try {
            int len = s.readRawVarint32();
            int pos = CodedOutputStream.computeSInt32SizeNoTag(len);
            if(readableLength - pos < len)
            {
                return null;
            }

            ProtoBufPacket imPacket = new ProtoBufPacket();
            s = CodedInputStream.newInstance(buffer);
            byte[] dst = s.readByteArray();
            buffer.position(buffer.position() + s.getTotalBytesRead());
            imPacket.setbody(dst);
            return imPacket;

        }
        catch (Exception ex)
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            logger.error(sw.toString());
            return null;
        }
    }


}
