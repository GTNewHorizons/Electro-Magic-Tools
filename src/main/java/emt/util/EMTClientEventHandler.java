package emt.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.relauncher.Side;
import emt.EMT;
import emt.client.EMTKeys;
import emt.init.EMTItems;
import emt.network.PacketEMTKeys;
import ic2.core.IC2;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;

public class EMTClientEventHandler {

    public AudioSource audio;
    public Item lastItem;

    @SubscribeEvent
    public void clientTick(ClientTickEvent e) {
        if (EMTKeys.keyUnequip.isPressed()) {
            EMT.INSTANCE.sendToServer(new PacketEMTKeys());
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent e) {
        if (e.side == Side.CLIENT) {

            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            Item curItem = player.inventory.getCurrentItem() == null ? null
                    : player.inventory.getCurrentItem().getItem();

            if (curItem == EMTItems.diamondChainsaw || curItem == EMTItems.streamChainsaw
                    || curItem == EMTItems.thaumiumChainsaw) {
                if (audio == null) audio = IC2.audioManager.createSource(
                        player,
                        PositionSpec.Hand,
                        "Tools/Chainsaw/ChainsawIdle.ogg",
                        true,
                        false,
                        IC2.audioManager.getDefaultVolume());
                if (audio != null) {
                    audio.updatePosition();
                    audio.play();
                }
            }

            if (audio != null && curItem != lastItem) {
                audio.stop();
                audio.remove();
                audio = null;
                IC2.audioManager.playOnce(
                        player,
                        PositionSpec.Hand,
                        "Tools/Chainsaw/ChainsawStop.ogg",
                        true,
                        IC2.audioManager.getDefaultVolume());
            }

            lastItem = curItem;
        }
    }
}
