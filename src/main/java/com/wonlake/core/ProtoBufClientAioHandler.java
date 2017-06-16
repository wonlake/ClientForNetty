package com.wonlake.core;

import com.wonlake.pb.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;

/**
 * Created by Administrator on 2017/5/12.
 */
public class ProtoBufClientAioHandler extends ProtoBufAioHandler
        implements ClientAioHandler<Object, ProtoBufPacket, Object>
{
    private static ProtoBufPacket m_HeartbeatPacket = new ProtoBufPacket();
    private static Logger logger = LoggerFactory.getLogger(ProtoBufClientAioHandler.class);

    @Override
    public ProtoBufPacket heartbeatPacket()
    {
        return  null;//m_HeartbeatPacket;
    }

    @Override
    public Object handler(ProtoBufPacket packet,
                          ChannelContext<Object, ProtoBufPacket, Object> channelContext) throws Exception
    {
        byte[] body = packet.getBody();
        if(body != null)
        {
            Messages.MessageWrapper msg = Messages.MessageWrapper.parseFrom(body);
            Messages.Person p = Messages.Person.parseFrom(msg.getMessage());
            logger.info(p.getName());
        }
        return null;
    }
}
