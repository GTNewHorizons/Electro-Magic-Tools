package emt.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;

import cpw.mods.fml.common.registry.EntityRegistry;
import emt.EMT;
import emt.entity.EntityArcher;
import emt.entity.EntityEnergyBall;
import emt.entity.EntityLaser;
import emt.entity.EntityShield;

public class EMTEntities {

    private static int startEID = 300;
    private static int entityIDs = 0;

    private static int getUniqueEntityID() {
        do {
            startEID++;
        } while (EntityList.getStringFromID(startEID) != null);
        return startEID;
    }

    public static void registerEntityEgg(Class<? extends Entity> entity, int colPrim, int colSec) {
        int id = getUniqueEntityID();
        EntityList.IDtoClassMapping.put(id, entity);
        EntityList.entityEggs.put(id, new EntityEggInfo(id, colPrim, colSec));
        return;
    }

    public static void registerEMTEntities() {
        EntityRegistry.registerModEntity(EntityLaser.class, "laser", entityIDs++, EMT.instance, 80, 3, true);
        EntityRegistry.registerModEntity(EntityArcher.class, "archer", entityIDs++, EMT.instance, 80, 3, true);
        registerEntityEgg(EntityArcher.class, 0x99111F, 0xE5685);
        EntityRegistry.registerModEntity(EntityShield.class, "shield", entityIDs++, EMT.instance, 80, 3, true);
        EntityRegistry.registerModEntity(EntityEnergyBall.class, "energyBall", entityIDs++, EMT.instance, 80, 60, true);
    }
}
