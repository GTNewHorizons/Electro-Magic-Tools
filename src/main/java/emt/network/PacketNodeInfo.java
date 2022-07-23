package emt.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import emt.tile.GT_MetaTileEntity_ResearchCompleter;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;

public class PacketNodeInfo implements IMessage, IMessageHandler<PacketNodeInfo, IMessage> {

    private int mBlockX, mBlockY, mBlockZ, mDim, nodeDistance, nodeColor;

    public PacketNodeInfo() {}

    public PacketNodeInfo(int mBlockX, int mBlockY, int mBlockZ, int mDim, int nodeDistance, int nodeColor) {
        this.mBlockX = mBlockX;
        this.mBlockY = mBlockY;
        this.mBlockZ = mBlockZ;
        this.mDim = mDim;
        this.nodeDistance = nodeDistance;
        this.nodeColor = nodeColor;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        mBlockX = buf.readInt();
        mBlockY = buf.readInt();
        mBlockZ = buf.readInt();
        mDim = buf.readInt();
        nodeDistance = buf.readInt();
        nodeColor = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(mBlockX);
        buf.writeInt(mBlockY);
        buf.writeInt(mBlockZ);
        buf.writeInt(mDim);
        buf.writeInt(nodeDistance);
        buf.writeInt(nodeColor);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(PacketNodeInfo message, MessageContext ctx) {
        WorldClient world = Minecraft.getMinecraft().theWorld;
        if (world != null) {
            if (world.blockExists(message.mBlockX, message.mBlockY, message.mBlockZ)) {
                TileEntity tileEntity = world.getTileEntity(message.mBlockX, message.mBlockY, message.mBlockZ);
                if (tileEntity instanceof IGregTechTileEntity) {
                    IGregTechTileEntity gtTile = (IGregTechTileEntity) tileEntity;
                    if (gtTile.getMetaTileEntity() instanceof GT_MetaTileEntity_ResearchCompleter) {
                        GT_MetaTileEntity_ResearchCompleter researchCompleter = (GT_MetaTileEntity_ResearchCompleter) gtTile.getMetaTileEntity();
                        researchCompleter.setNodeValues(message.nodeDistance, message.nodeColor);
                    }
                }
            }
        }
        return null;
    }
}
