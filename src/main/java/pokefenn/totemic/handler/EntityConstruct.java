package pokefenn.totemic.handler;

import java.util.Random;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityConstruct {
    @SubscribeEvent
    public void onEntityConstruct(EntityConstructing event) {
        if(event.getEntity() instanceof EntityVillager) {
            //Work around an issue in Forge/Minecraft where the world's RNG seed is set to the same value every time before a villager is spawned,
            //causing the same profession to be chosen "randomly" for all the villagers.
            event.getEntity().world.rand.setSeed(new Random().nextLong());
        }
    }
}
