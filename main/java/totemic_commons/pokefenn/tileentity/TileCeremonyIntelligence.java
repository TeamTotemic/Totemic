package totemic_commons.pokefenn.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.block.plant.IPlantDrain;
import totemic_commons.pokefenn.recipe.registry.CeremonyRegistry;
import totemic_commons.pokefenn.lib.PlantIds;
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
    public int drainedFungus;
    public int efficiency;

    public TileCeremonyIntelligence()
    {
        isBurning = false;
        isDoingEffect = false;
        hasValidPlants = false;
        currentCeremony = 0;
        socketAmount = 0;
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
        drainedFungus = 0;
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
        if(!this.worldObj.isRemote)
        {
            if(this.worldObj.getWorldTime() % 60L == 0)
            {
                //this.isBurning = this.worldObj.getBlock(xCoord + 8, yCoord, zCoord) == ModBlocks.totemTorch && this.worldObj.getBlock(xCoord, yCoord, zCoord + 8) == ModBlocks.totemTorch && this.worldObj.getBlock(xCoord - 8, yCoord, zCoord) == ModBlocks.totemTorch && this.worldObj.getBlock(xCoord, yCoord, zCoord - 8) == ModBlocks.totemTorch;
            }

            if(currentCeremony <= CeremonyRegistry.ceremonyRegistry.size() && currentCeremony != 0)
            {
                if(!CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).doesLastForever() && currentTime >= CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getMaximumTicks())
                {
                    resetEverything();
                    return;
                }

                if(isDoingEffect && !CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).doesLastForever())
                    currentTime++;

                ICeremonyEffect effect = CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyEffect();

                if(!isDoingEffect && canStartCeremony(CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getOverallDrain(), CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getPlantForPercentage(), CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getPercentage()))
                {
                    if(effect != null)
                    {
                        isDoingEffect = true;
                        CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyEffect().effect(this);
                    }
                } else if(worldObj.getWorldTime() % 10L == 0L && !CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).doesLastForever())
                {
                    this.drainPlant();
                }

                if(worldObj.getWorldTime() % 100 == 0)
                    if(CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).doesLastForever())
                        this.drainPlant();

                if(worldObj.getWorldTime() % 100 == 0)
                    if(CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).doesLastForever())
                        if(overallDrained < CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCostPer5Seconds())
                            resetEverything();

            }
        }
    }

    public void resetEverything()
    {
        currentCeremony = 0;
        currentTime = 0;
        efficiency = 0;
        isDoingEffect = false;
        overallDrained = 0;
        drainedWheat = 0;
        drainedCarrot = 0;
        drainedPotato = 0;
        drainedMelon = 0;
        drainedPumpkin = 0;
        drainedMoonglow = 0;
        drainedBloodwart = 0;
        drainedLotus = 0;
        drainedFungus = 0;
    }

    public int getDrained(int i)
    {
        if(i == PlantIds.BLOODWART_ID)
            return drainedBloodwart;
        if(i == PlantIds.FUNGUS_ID)
            return drainedFungus;
        if(i == PlantIds.LOTUS_ID)
            return drainedLotus;
        if(i == PlantIds.MOONGLOW_ID)
            return drainedMoonglow;
        if(i == PlantIds.MELON_ID)
            return drainedMelon;
        if(i == PlantIds.WHEAT_ID)
            return drainedWheat;
        if(i == PlantIds.CARROT_ID)
            return drainedCarrot;
        if(i == PlantIds.POTATO_ID)
            return drainedPotato;

        return 0;
    }

    public void drainEssence(int i)
    {
        //Todo correct drained essence code
        overallDrained = (drainedWheat + drainedCarrot + drainedPotato + drainedMelon + drainedPumpkin + drainedMoonglow + drainedBloodwart + drainedLotus);
    }

    public void trackPlayersMovements()
    {
        if(worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 8) != null)
        {
            EntityPlayer playerDance = worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 8);
            double oldPosX = playerDance.lastTickPosX;
            double oldPosY = playerDance.lastTickPosY;
            double oldPosZ = playerDance.lastTickPosZ;

            if(worldObj.getWorldTime() % 40L == 0 && efficiency < 50)
            {
                if(oldPosX <= playerDance.posX - 3 || oldPosX <= playerDance.posX + 3 || oldPosX >= playerDance.posX - 3 || oldPosX >= playerDance.posX + 3)
                    if(oldPosZ <= playerDance.posZ - 3 || oldPosZ <= playerDance.posZ + 3 || oldPosZ >= playerDance.posZ - 3 || oldPosZ >= playerDance.posZ + 3)
                        efficiency += 4;

            }
        }

    }

    public void tryCeremony(TileCeremonyIntelligence tileCeremonyIntelligence)
    {
        int x = tileCeremonyIntelligence.xCoord;
        int y = tileCeremonyIntelligence.yCoord;
        int z = tileCeremonyIntelligence.zCoord;

        World world = tileCeremonyIntelligence.getWorldObj();

        if(tileCeremonyIntelligence.getWorldObj().getBlock(x + 3, y, z) instanceof IPlantable && tileCeremonyIntelligence.getWorldObj().getBlock(x - 3, y, z) instanceof IPlantable && tileCeremonyIntelligence.getWorldObj().getBlock(x, y, z + 3) instanceof IPlantable && tileCeremonyIntelligence.getWorldObj().getBlock(x, y, z - 3) instanceof IPlantable)
        {
            for(CeremonyRegistry ceremonyRegistry : CeremonyRegistry.ceremonyRegistry)
            {
                //if (isBurning)
                {
                    if(ceremonyRegistry.getDoesNeedItems())
                    {
                        if(EntityUtil.getEntitiesInRange(world, x, y, z, 0, 3) != null)
                        {
                            for(Entity entity : EntityUtil.getEntitiesInRange(world, x, y, z, 0, 3))
                            {
                                if(entity instanceof EntityItem)
                                {
                                    if(ceremonyRegistry.getItem() != null)
                                    {
                                        if(((EntityItem) entity).getEntityItem().getItem() == ceremonyRegistry.getItem().getItem() && ((EntityItem) entity).getEntityItem().getItemDamage() == ceremonyRegistry.getItem().getItemDamage())
                                        {
                                            if(arePlantsValid(ceremonyRegistry))
                                            {
                                                ((EntityItem) entity).setDead();
                                                this.currentCeremony = ceremonyRegistry.getCeremonyID();
                                                //preformCeremony(ceremonyRegistry);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if(arePlantsValid(ceremonyRegistry))
                    {
                        this.currentCeremony = ceremonyRegistry.getCeremonyID();
                        System.out.println(currentCeremony);
                        //this.preformCeremony(ceremonyRegistry);
                    }
                }
            }

        }
    }

    public static int getIdFromPlant(Block block)
    {
        if(block != null)
        {
            if(block == Blocks.wheat)
                return PlantIds.WHEAT_ID;
            if(block == Blocks.potatoes)
                return PlantIds.POTATO_ID;
            if(block == Blocks.carrots)
                return PlantIds.CARROT_ID;
            if(block == Blocks.melon_stem)
                return PlantIds.MELON_ID;
            if(block == Blocks.pumpkin_stem)
                return PlantIds.PUMPKIN_ID;
            if(block == ModBlocks.moonglow)
                return PlantIds.MOONGLOW_ID;
            if(block == ModBlocks.lotusBlock)
                return PlantIds.LOTUS_ID;
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

        if(plant1 != null && plant2 != null && plant3 != null && plant4 != null && getIdFromPlant(world.getBlock(x + 3, y, z)) != 0 && getIdFromPlant(world.getBlock(x - 3, y, z)) != 0 && getIdFromPlant(world.getBlock(x, y, z + 3)) != 0 && getIdFromPlant(world.getBlock(x, y, z - 3)) != 0)
        {
            if(possibility1)
            {
                return true;
            }
        }

        return false;
    }

    public boolean canStartCeremony(int overall, int plant, int percentage)
    {
        if(overallDrained > overall)
        {
            if(getDrained(plant) != 0 && overall % percentage >= getDrained(plant))
            {
                this.overallDrained = 0;
                return true;
            }
        }

        return false;
    }

    public void drainPlant()
    {
        World world = this.worldObj;

        int radius = 8;
        int yAxis = 3;

        trackPlayersMovements();

        for(int i = -radius; i <= radius; i++)
            //for (int j = -radius; j <= radius; j++)
            for(int k = -radius; k <= radius; k++)
            {
                Block plantSelected = world.getBlock(xCoord + i, yCoord, zCoord + k);
                if(plantSelected != null)
                {

                    boolean isNotFlower = !plantSelected.getUnlocalizedName().contains("flower");
                    boolean isNotBush = !plantSelected.getUnlocalizedName().contains("bush");
                    boolean isNotBerry = !plantSelected.getUnlocalizedName().contains("berry");
                    boolean isNotKelp = !plantSelected.getUnlocalizedName().contains("kelp");

                    efficiency += getEffiencyFromBlock(plantSelected);

                    if(plantSelected instanceof IPlantable && isNotFlower && isNotBerry && isNotBush && isNotKelp)
                    {
                        if(world.getBlockMetadata(xCoord + i, yCoord, zCoord + k) > 2)
                        {
                            world.setBlockMetadataWithNotify(xCoord + i, yCoord, zCoord + k, world.getBlockMetadata(xCoord + i, yCoord, zCoord + k) - 1, 2);
                            getVariableForDrain(plantSelected, getPlantDrained(plantSelected));
                            overallDrained = (drainedWheat + drainedCarrot + drainedPotato + drainedMelon + drainedPumpkin + drainedMoonglow + drainedBloodwart + drainedLotus);
                        }
                    }
                }
            }
    }

    public void getVariableForDrain(Block block, int i)
    {
        if(block == Blocks.wheat)
            drainedWheat += i;
        if(block == Blocks.carrots)
            drainedCarrot += i;
        if(block == Blocks.potatoes)
            drainedPotato += i;
        if(block == Blocks.melon_stem)
            drainedMelon += i;
        if(block == Blocks.pumpkin_stem)
            drainedPumpkin += i;
        if(block == ModBlocks.moonglow)
            drainedMoonglow += i;
        if(block == ModBlocks.bloodwart)
            drainedBloodwart += i;
        if(block == ModBlocks.lotusBlock)
            drainedLotus += i;
        if(block == ModBlocks.fungusBlock)
            drainedFungus += i;
    }

    public int getEffiencyFromBlock(Block block)
    {
        if(efficiency < 50)
        {
            if(block == ModBlocks.totemTorch)
                return 5;
            if(block == ModBlocks.totemSocket)
                return 3;
            if(block == Blocks.torch)
                return 1;
            if(block == Blocks.fire)
                return 2;
            if(block == ModBlocks.flameParticle)
                return 4;
        }

        return 0;
    }


    public int getPlantDrained(Block plant)
    {
        if(plant instanceof IPlantDrain)
        {
            return ((IPlantDrain) plant).getPlantDrain(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
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
