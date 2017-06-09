package totemic_commons.pokefenn;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public final class CapabilityMovementTracker
{
    @CapabilityInject(MovementTracker.class)
    public static final Capability<MovementTracker> CAPABILITY = null;

    public interface MovementTracker
    {
        double getPrevPosX();
        double getPrevPosY();
        double getPrevPosZ();

        void update(Entity entity);
    }

    static class MovementTrackerImpl implements MovementTracker
    {
        double prevPosX, prevPosY, prevPosZ;

        @Override
        public double getPrevPosX() { return prevPosX; }
        @Override
        public double getPrevPosY() { return prevPosY; }
        @Override
        public double getPrevPosZ() { return prevPosZ; }

        @Override
        public void update(Entity entity)
        {
            prevPosX = entity.posX;
            prevPosY = entity.posY;
            prevPosZ = entity.posZ;
        }
    }

    static void register()
    {
        CapabilityManager.INSTANCE.register(MovementTracker.class, new Capability.IStorage<MovementTracker>()
        {
            @Override
            public NBTBase writeNBT(Capability<MovementTracker> capability, MovementTracker instance, EnumFacing side)
            {
                NBTTagList list = new NBTTagList();
                list.appendTag(new NBTTagDouble(instance.getPrevPosX()));
                list.appendTag(new NBTTagDouble(instance.getPrevPosY()));
                list.appendTag(new NBTTagDouble(instance.getPrevPosZ()));
                return list;
            }

            @Override
            public void readNBT(Capability<MovementTracker> capability, MovementTracker instance, EnumFacing side, NBTBase nbt)
            {
                MovementTrackerImpl impl = (MovementTrackerImpl) instance;
                NBTTagList list = (NBTTagList) nbt;
                impl.prevPosX = list.getDoubleAt(0);
                impl.prevPosY = list.getDoubleAt(1);
                impl.prevPosZ = list.getDoubleAt(2);
            }
        },
        MovementTrackerImpl::new);
    }
}
