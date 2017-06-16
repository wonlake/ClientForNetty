package com.wonlake.core;

import org.tio.core.intf.Packet;

/**
 * Created by Administrator on 2017/5/12.
 */
public class ProtoBufPacket extends Packet
{
    private byte[] m_body;
    public byte[] getBody()
    {
        return m_body;
    }
    public void setbody(byte[] body)
    {
        this.m_body = body;
    }
}
