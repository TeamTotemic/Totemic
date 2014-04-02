package totemic_commons.pokefenn.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.block.plant.IPlantDrain;
import totemic_commons.pokefenn.ceremony.CeremonyRegistry;
import totemic_commons.pokefenn.lib.PlantIds;
import totemic_commons.pokefenn.network.AbstractPacket;
import totemic_commons.pokefenn.network.PacketPipeline;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TileCeremonyIntelligence extends TileTotemic
{
    public boolean isBurning;
    public boolean isDoingEffect;
    public boolean hasValidPlants;
    public int currentCeremony;
    public int overallDrained;
    public int socketAmount;
    public int shiftedCeremonyValue;
    public String player;
    public int currentTime;
    public int drainedWheat;
    public int drainedCarrot;
    public int drainedPotato;
    public int drainedMelon;
    public int drainedPumpkin;
    public int drainedMoonglow;
    public int drainedBloodwart;
    public int drainedLotus;
    public int efficiency;

    public TileCeremonyIntelligence()
    {
        isBurning = false;
        isDoingEffect = false;
        hasValidPlants = false;
        currentCeremony = 0;
        socketAmount = 0;
        shiftedCeremonyValue = currentCeremony + 1;
        player = "";
        currentTime = 0;
        drainedWheat = 0;
        drainedCarrot = 0;
        drainedPotato = 0;
        drainedMelon = 0;
        drainedPumpkin = 0;
        drainedMoonglow = 0;
        drainedBloodwart = 0;
        drainedLotus = 0;
        overallDrained = 0;
        efficiency = 0;
    }

    @Override
    public boolean canUpdate()
    {
        return true;
    }

    @Override
    public void updateEntity()
    {
        if (!this.worldObj.isRemote)
        {
            if (this.worldObj.getWorldTime() % 60L == 0)
            {
                this.isBurning = this.worldObj.getBlock(xCoord + 8, yCoord, zCoord) == ModBlocks.totemTorch && this.worldObj.getBlock(xCoord, yCoord, zCoord + 8) == ModBlocks.totemTorch && this.worldObj.getBlock(xCoord - 8, yCoord, zCoord) == ModBlocks.totemTorch && this.worldObj.getBlock(xCoord, yCoord, zCoord - 8) == ModBlocks.totemTorch;
            }

            //Note: All ceremonyRegistry ids have to be shifted down by one, so it gets the correct ritual. the 0 in currentCeremony means that there is a null effect more or less.

            if (currentCeremony <= CeremonyRegistry.ceremonyRegistry.size() && currentCeremony != 0)
            {
                ICeremonyEffect effect = CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyEffect();

                if (canStartCeremony(CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getOverallDrain()))
                {
                    if (effect != null)
                    {
                        System.out.println(currentCeremony);
                        effect.effect(this);
                    }
                } else
                    this.drainPlantsAtStart(CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getPercentage(), CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getPlantForPercentage());
            }
        }

    }

    public boolean tryCeremony(TileCeremonyIntelligence tileCeremonyIntelligence)
    {
        int x = tileCeremonyIntelligence.xCoord;
        int y = tileCeremonyIntelligence.yCoord;
        int z = tileCeremonyIntelligence.zCoord;

        World world = tileCeremonyIntelligence.getWorldObj();

        if (tileCeremonyIntelligence.getWorldObj().getBlock(x + 3, y, z) instanceof IPlantable && tileCeremonyIntelligence.getWorldObj().getBlock(x - 3, y, z) instanceof IPlantable && tileCeremonyIntelligence.getWorldObj().getBlock(x, y, z + 3) instanceof IPlantable && tileCeremonyIntelligence.getWorldObj().getBlock(x, y, z - 3) instanceof IPlantable)
        {
            for (CeremonyRegistry ceremonyRegistry : CeremonyRegistry.ceremonyRegistry)
            {
                if (isBurning)
                {
                    if (ceremonyRegistry.getDoesNeedItems())
                    {
                        if (EntityUtil.getEntitiesInRange(world, x, y, z, 0, 3) != null)
                        {
                            for (Entity entity : EntityUtil.getEntitiesInRange(world, x, y, z, 0, 3))
                            {
                                if (entity instanceof EntityItem)
                                {
                                    if (ceremonyRegistry.getItem() != null)
                                    {
                                        if (((EntityItem) entity).getEntityItem().getItem() == ceremonyRegistry.getItem().getItem() && ((EntityItem) entity).getEntityItem().getItemDamage() == ceremonyRegistry.getItem().getItemDamage())
                                        {
                                            if (arePlantsValid(ceremonyRegistry))
                                            {
                                                ((EntityItem) entity).setDead();
                                                preformCeremony(ceremonyRegistry);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (arePlantsValid(ceremonyRegistry))
                    {
                        this.preformCeremony(ceremonyRegistry);
                    }
                }
            }

        }
        return false;
    }

    public static int getIdFromPlant(Block block)
    {
        if (block != null)
        {
            if (block == Blocks.wheat)
                return PlantIds.WHEAT_ID;
            if (block == Blocks.potatoes)
                return PlantIds.POTATO_ID;
            if (block == Blocks.carrots)
                return PlantIds.CARROT_ID;
            if (block == Blocks.melon_stem)
                return PlantIds.MELON_ID;
            if (block == Blocks.pumpkin_stem)
                return PlantIds.PUMPKIN_ID;
            if (block == ModBlocks.moonglow)
                return PlantIds.MOONGLOW_ID;
        }

        return 0;
    }


    public boolean arePlantsValid(CeremonyRegistry ceremonyRegistry)
    {
        World world = this.worldObj;

        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;

        Block plant1 = world.getBlock(x + 3, y, z);
        Block plant2 = world.getBlock(x - 3, y, z);
        Block plant3 = world.getBlock(x, y, z + 3);
        Block plant4 = world.getBlock(x, y, z - 3);

        int plantID1 = ceremonyRegistry.getPlant1();
        int plantID2 = ceremonyRegistry.getPlant2();
        int plantID3 = ceremonyRegistry.getPlant3();
        int plantID4 = ceremonyRegistry.getPlant4();

        boolean possibility1 = plantID1 == getIdFromPlant(plant1) && plantID2 == getIdFromPlant(plant2) && plantID3 == getIdFromPlant(plant3) && plantID4 == getIdFromPlant(plant4);
        //boolean possibility2 = plantID1 == getIdFromPlant(plant1) && plantID2 == getIdFromPlant(plant2) && plantID3 == getIdFromPlant(plant3) && plantID4 == getIdFromPlant(plant4);

        if (getIdFromPlant(world.getBlock(x + 3, y, z)) != 0 && getIdFromPlant(world.getBlock(x - 3, y, z)) != 0 && getIdFromPlant(world.getBlock(x, y, z + 3)) != 0 && getIdFromPlant(world.getBlock(x, y, z - 3)) != 0)
        {
            if (possibility1)
            {
                return true;
            }
        }

        return false;
    }

    public void preformCeremony(CeremonyRegistry ceremonyRegistry)
    {
        this.currentCeremony = ceremonyRegistry.getCeremonyID();

        //while (!canStartCeremony(ceremonyRegistry.getOverallDrain()))
        {
            //    this.drainPlantsAtStart(ceremonyRegistry.getPercentage(), ceremonyRegistry.getPlantForPercentage());
        }

        if (canStartCeremony(ceremonyRegistry.getOverallDrain()))
            ceremonyRegistry.getCeremonyEffect().effect(this);

        this.efficiency = 0;

    }

    public boolean canStartCeremony(int overall)
    {
        if (overallDrained > overall)
        {
            this.overallDrained = 0;
            return true;
        }

        return false;
    }

    public void drainPlantsAtStart(int percentage, int plant)
    {
        World world = this.worldObj;

        int radius = 8;
        int yAxis = 3;

        for (int i = -radius; i <= radius; i++)
            for (int j = -radius; j <= radius; j++)
                for (int k = -yAxis; k <= yAxis; j++)
                {
                    Block plantSelected = world.getBlock(xCoord + i, yCoord + j, zCoord + k);
                    if (plantSelected != null)
                    {

                        boolean isNotFlower = !plantSelected.getUnlocalizedName().contains("flower");
                        boolean isNotBush = !plantSelected.getUnlocalizedName().contains("bush");
                        boolean isNotBerry = !plantSelected.getUnlocalizedName().contains("berry");
                        boolean isNotKelp = !plantSelected.getUnlocalizedName().contains("kelp");

                        efficiency += getEffiencyFromBlock(plantSelected);

                        if (plantSelected instanceof IPlantable && isNotFlower && isNotBerry && isNotBush && isNotKelp)
                        {
                            if (world.getBlockMetadata(xCoord + i, yCoord + j, zCoord + k) > 2)
                            {
                                world.setBlockMetadataWithNotify(xCoord + i, yCoord + j, zCoord + k, world.getBlockMetadata(xCoord + i, yCoord + j, zCoord + k) - 1, 2);
                                getVariableForDrain(plantSelected, getPlantDrained(plantSelected));
                                overallDrained = (drainedWheat + drainedCarrot + drainedPotato + drainedMelon + drainedPumpkin + drainedMoonglow + drainedBloodwart + drainedLotus);
                            }
                        }
                    }
                }
    }

    public void getVariableForDrain(Block block, int i)
    {
        if (block == Blocks.wheat)
            drainedWheat += i;
        if (block == Blocks.carrots)
            drainedCarrot += i;
        if (block == Blocks.potatoes)
            drainedPotato += i;
        if (block == Blocks.melon_stem)
            drainedMelon += i;
        if (block == Blocks.pumpkin_stem)
            drainedPumpkin += i;
        if (block == ModBlocks.moonglow)
            drainedMoonglow += i;
        if (block == ModBlocks.bloodwart)
            drainedBloodwart += i;
        if (block == ModBlocks.lotusBlock)
            drainedLotus += i;
    }

    public int getEffiencyFromBlock(Block block)
    {
        if (block == ModBlocks.totemTorch)
            return 5;
        if (block == ModBlocks.totemSocket)
            return 3;
        if (block == Blocks.torch)
            return 1;
        if (block == Blocks.fire)
            return 2;

        return 0;
    }


    public int getPlantDrained(Block plant)
    {
        if (plant instanceof IPlantDrain)
        {
            ((IPlantDrain) plant).getPlantDrain(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        }

        return 2;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setBoolean("isBurning", isBurning);
        nbtTagCompound.setBoolean("isDoingEffect", isDoingEffect);
        nbtTagCompound.setBoolean("hasValidPlants", hasValidPlants);
        nbtTagCompound.setInteger("currentCeremony", currentCeremony);
        nbtTagCompound.setInteger("overallDrained", overallDrained);
        nbtTagCompound.setString("player", player);
        nbtTagCompound.setInteger("currentTime", currentTime);
        nbtTagCompound.setInteger("drainedWheat", drainedWheat);
        nbtTagCompound.setInteger("drainedCarrot", drainedCarrot);
        nbtTagCompound.setInteger("drainedPotato", drainedPotato);
        nbtTagCompound.setInteger("drainedMelon", drainedMelon);
        nbtTagCompound.setInteger("drainedPumpkin", drainedPumpkin);
        nbtTagCompound.setInteger("drainedMoonglow", drainedMoonglow);
        nbtTagCompound.setInteger("drainedBloodwart", drainedBloodwart);
        nbtTagCompound.setInteger("drainedLotus", drainedLotus);
        nbtTagCompound.setInteger("efficiency", efficiency);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        isBurning = nbtTagCompound.getBoolean("isBurning");
        isDoingEffect = nbtTagCompound.getBoolean("isDoingEffect");
        hasValidPlants = nbtTagCompound.getBoolean("hasValidPlants");
        currentCeremony = nbtTagCompound.getInteger("currentCeremony");
        overallDrained = nbtTagCompound.getInteger("overallDrained");
        player = nbtTagCompound.getString("player");
        drainedWheat = nbtTagCompound.getInteger("drainedWheat");
        drainedCarrot = nbtTagCompound.getInteger("drainedCarrot");
        drainedPotato = nbtTagCompound.getInteger("drainedPotato");
        drainedMelon = nbtTagCompound.getInteger("drainedMelon");
        drainedPumpkin = nbtTagCompound.getInteger("drainedPumpkin");
        drainedMoonglow = nbtTagCompound.getInteger("drainedMoonglow");
        drainedBloodwart = nbtTagCompound.getInteger("drainedBloodwart");
        drainedLotus = nbtTagCompound.getInteger("drainedLotus");
        efficiency = nbtTagCompound.getInteger("efficiency");
        currentTime = nbtTagCompound.getInteger("currentTime");
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
}
