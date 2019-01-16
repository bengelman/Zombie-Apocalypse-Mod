package com.pdogmuncher.zombieapocalypse.events;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MyMessage implements IMessage {
    
    private int x;
    private int y;

    public MyMessage() { }

    public MyMessage(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = Integer.parseInt(ByteBufUtils.readUTF8String(buf));
        y = Integer.parseInt(ByteBufUtils.readUTF8String(buf));// this class is very useful in general for writing more complex objects
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, x + "");
        ByteBufUtils.writeUTF8String(buf, y + "");
    }

    public static class Handler implements IMessageHandler<MyMessage, IMessage> {
        
        @Override
        public IMessage onMessage(MyMessage message, MessageContext ctx) {
            System.out.println(String.format("Received %s from %s", message.x, ctx.getServerHandler().playerEntity.getDisplayName()));
            
            return null; // no response in this case
        }
    }
}