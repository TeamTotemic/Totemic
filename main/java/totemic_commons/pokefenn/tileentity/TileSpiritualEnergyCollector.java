package totemic_commons.pokefenn.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import totemic_commons.pokefenn.api.totem.ISpiritualEnergyInput;
import totemic_commons.pokefenn.misc.villager.TotemicVillagerInitiation;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TileSpiritualEnergyCollector extends TileTotemic
{

    public int currentEnergy;
    public int currentlyProducing;
    public int currentPraising;
    public int maximumPraising;

    public TileSpiritualEnergyCollector()
    {
        currentEnergy = 0;
        currentlyProducing = 0;
        currentPraising = 0;
        maximumPraising = 0;
    }

    public boolean canUpdate()
    {
        return true;
    }

    public void updateEntity()
    {
        int x = xCoord;
        int y = yCoord;
        int z = zCoord;
        World world = worldObj;

        if(!worldObj.isRemote)
        {

            if(world.getWorldTime() % 40L == 0)
            {
                transferSpiritualEnergy();
            }

            if(world.getWorldTime() % 30L == 0)
                if(currentPraising <= maximumPraising)
                {
                    if(EntityUtil.getEntitiesInRange(world, x, y, z, 20, 20) != null)
                    {
                        for(Entity entity : EntityUtil.getEntitiesInRange(world, x, y, z, 20, 20))
                        {
                            if(entity instanceof EntityVillager)
                            {
                                if(((EntityVillager) entity).getProfession() == TotemicVillagerInitiation.SHAMAN_VILLAGER_ID)
                                {
                                    currentlyProducing += 3;
                                    currentPraising++;

                                } else
                                {
                                    currentlyProducing += 2;
                                    currentPraising++;
                                }
                            }

                            if(entity instanceof EntityAnimal)
                            {
                                currentlyProducing++;
                                currentPraising++;
                            }

                        }
                    }
                } else
                {
                    currentPraising = 0;
                    currentlyProducing = 0;
                }
        }

    }


    public void transferSpiritualEnergy()
    {
        int x = xCoord;
        int y = yCoord;
        int z = zCoord;
        World world = worldObj;
        int range = 16;

        for(int i = -range; i <= range; i++)
            for(int j = -range; j <= range; j++)
                for(int k = -range; k <= range; k++)
                {
                    if(world.getBlock(x + i, y + j, z + k) != null)
                    {
                        Block block = world.getBlock(x + i, y + j, z + k);

                        if(block instanceof ISpiritualEnergyInput)
                        {
                            if(((ISpiritualEnergyInput) block).canReceiveSpiritualEnergy(currentEnergy))
                            {
                                ((ISpiritualEnergyInput) block).receiveSpiritualEnergy(currentEnergy);
                                currentEnergy = 0;
                            }
                        }
                    }
                }

    }

    public int getSpiritualValue()
    {
        World world = worldObj;
        int x = xCoord;
        int y = yCoord;
        int z = zCoord;

        int value = 0;

        BiomeGenBase biome = world.getBiomeGenForCoords(x, y);


        return value;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.func_148857_g());
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setInteger("currentEnergy", currentEnergy);
        nbtTagCompound.setInteger("currentlyProducing", currentlyProducing);
        nbtTagCompound.setInteger("currentPraising", currentPraising);
        nbtTagCompound.setInteger("maximumPraising", maximumPraising);

    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        currentEnergy = nbtTagCompound.getInteger("currentEnergy");
        currentlyProducing = nbtTagCompound.getInteger("currentlyProducing");
        currentPraising = nbtTagCompound.getInteger("currentPraising");
        maximumPraising = nbtTagCompound.getInteger("maximumPraising");

    }

}
