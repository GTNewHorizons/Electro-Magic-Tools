package emt.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import emt.init.EMTItems;
import io.netty.buffer.ByteBuf;

public class PacketEMTKeys implements IMessage, IMessageHandler<PacketEMTKeys, IMessage> {

    @Override
    public IMessage onMessage(PacketEMTKeys message, MessageContext ctx) {
        if (ctx.getServerHandler().playerEntity.inventory.armorInventory[2] != null
                && ctx.getServerHandler().playerEntity.inventory.armorInventory[2].getItem() == EMTItems.quantumArmor) {
            ctx.getServerHandler().playerEntity.inventory.armorInventory[2].stackTagCompound
                    .setBoolean("unequip", true);
        }
        return null;
    }

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}
}
