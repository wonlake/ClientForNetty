package com.wonlake.core;

import com.wonlake.pb.Messages;

/**
 * Created by meijun on 2017/5/21.
 */
public class UtilSend {
    public static ProtoBufPacket build(String name, int age) {
        ProtoBufPacket packet = new ProtoBufPacket();
        packet.setbody(
                Messages.MessageWrapper.newBuilder()
                        .setId(100)
                        .setMessage(
                                Messages.Person.newBuilder()
                                        .setName(name)
                                        .setAge(age)
                                        .build().toByteString())
                .build().toByteArray());
        return packet;
    }
}
