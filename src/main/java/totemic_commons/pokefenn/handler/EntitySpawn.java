package totemic_commons.pokefenn.handler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;
import totemic_commons.pokefenn.item.equipment.music.CapabilityJingleDressMovementTracker;
import totemic_commons.pokefenn.item.equipment.music.CapabilityJingleDressMovementTracker.MovementTracker;
import totemic_commons.pokefenn.item.equipment.music.CapabilityJingleDressMovementTracker.MovementTrackerImpl;

public class EntitySpawn
{
    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event)
    {
        if(event.getEntity() instanceof EntitySkeleton)
        {
            EntitySkeleton entity = (EntitySkeleton)event.getEntity();
            entity.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(entity, EntityBuffalo.class, true));
        }
    }

    @SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent<Entity> event)
    {
        if(event.getObject() instanceof EntityPlayerMP)
        {
            event.addCapability(new ResourceLocation(Totemic.MOD_ID, "movement_tracker"), new ICapabilitySerializable<NBTBase>()
            {
                private final MovementTracker tracker = new MovementTrackerImpl(event.getObject());

                @Override
                public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
                {
                    return capability == CapabilityJingleDressMovementTracker.CAPABILITY;
                }

                @Override
                @Nullable
                public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
                {
                    if(capability == CapabilityJingleDressMovementTracker.CAPABILITY)
                        return CapabilityJingleDressMovementTracker.CAPABILITY.cast(tracker);
                    return null;
                }

                @Override
                public NBTBase serializeNBT()
                {
                    return CapabilityJingleDressMovementTracker.CAPABILITY.writeNBT(tracker, null);
                }

                @Override
                public void deserializeNBT(NBTBase nbt)
                {
                    CapabilityJingleDressMovementTracker.CAPABILITY.readNBT(tracker, null, nbt);
                }
            });
        }
    }
}
