package totemic_commons.pokefenn.tileentity.totem;

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
import totemic_commons.pokefenn.api.music.IMusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.api.plant.IPlantDrain;
import totemic_commons.pokefenn.api.plant.PlantEnum;
import totemic_commons.pokefenn.recipe.registry.CeremonyRegistry;
import totemic_commons.pokefenn.lib.PlantIds;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.TotemUtil;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TileCeremonyIntelligence extends TileTotemic implements IMusicAcceptor
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
    public int dancingEfficiency;
    public int[] music;
    public int plantEfficiency;
    public int[] musicSelector;

    public TileCeremonyIntelligence()
    {
        isBurning = false;
        isDoingEffect = false;
        hasValidPlants = false;
        currentCeremony = 0;
        socketAmount = 0;
        player = "";
        currentTime = 0;
        overallDrained = 0;
        dancingEfficiency = 0;
        music = new int[MusicEnum.values().length];
        plantEfficiency = 0;
        musicSelector = new int[4];
    }

    @Override
    public boolean canUpdate()
    {
        return true;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if(!this.worldObj.isRemote)
        {

            for(int aMusic : music)
            {
                //    System.out.println(aMusic);
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

                //if(!isDoingEffect && canStartCeremony(CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getOverallDrain(), CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getPlantForPercentage(), CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getPercentage()))
                {
                    if(effect != null)
                    {
                        isDoingEffect = true;
                        CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyEffect().effect(this);
                    }
                } /*else*/
                if(worldObj.getWorldTime() % 10L == 0L && !CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).doesLastForever())
                {
                    this.drainPlant();
                }

                if(worldObj.getWorldTime() % 100 == 0)
                    if(CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).doesLastForever())
                        this.drainPlant();

                //if(worldObj.getWorldTime() % 100 == 0)
                   // if(CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).doesLastForever())
                   //     if(overallDrained < CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCostPer5Seconds())
                   //         resetEverything();

            }
        }
    }

    public void resetEverything()
    {
        currentCeremony = 0;
        currentTime = 0;
        dancingEfficiency = 0;
        isDoingEffect = false;
        overallDrained = 0;
    }

    public int getDrained(int i)
    {

        return 0;
    }

    public void drainEssence(int i)
    {
        //Todo correct drained essence code
        //overallDrained = (drainedWheat + drainedCarrot + drainedPotato + drainedMelon + drainedPumpkin + drainedMoonglow + drainedBloodwart + drainedLotus);
    }

    public void trackPlayersMovements()
    {
        if(worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 8) != null)
        {
            EntityPlayer playerDance = worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 8);
            double oldPosX = playerDance.lastTickPosX;
            double oldPosY = playerDance.lastTickPosY;
            double oldPosZ = playerDance.lastTickPosZ;

            System.out.println(oldPosX);
            System.out.println(oldPosZ);

            if(worldObj.getWorldTime() % 40L == 0 && dancingEfficiency < 50)
            {
                //TODO rewrite this, and make armour make a difference
                if(oldPosX <= playerDance.posX - 3 || oldPosX <= playerDance.posX + 3 || oldPosX >= playerDance.posX - 3 || oldPosX >= playerDance.posX + 3)
                    if(oldPosZ <= playerDance.posZ - 3 || oldPosZ <= playerDance.posZ + 3 || oldPosZ >= playerDance.posZ - 3 || oldPosZ >= playerDance.posZ + 3)
                        dancingEfficiency += 4;

            }
        }

    }

    public void getPlayerArmour()
    {
        World world = worldObj;

        EntityPlayer entity = world.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 10);

        if(entity != null)
        {
            int armour = TotemUtil.getArmourAmounts(entity);

            //efficiency += (armour * 5);
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
                                        //if(((EntityItem) entity).getEntityItem().getItem() == ceremonyRegistry.getItem().getItem() && ((EntityItem) entity).getEntityItem().getItemDamage() == ceremonyRegistry.getItem().getItemDamage())
                                        {
                                            if(arePlantsValid(ceremonyRegistry))
                                            {
                                                entity.setDead();
                                                this.currentCeremony = ceremonyRegistry.getCeremonyID();
                                                //preformCeremony(ceremonyRegistry);
                                                getPlayerArmour();
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
                        getPlayerArmour();
                        //this.preformCeremony(ceremonyRegistry);
                    }
                }
            }

        }
    }

    public static PlantEnum getEnumFromPlant(Block block)
    {
        if(block != null)
        {
            if(block == Blocks.wheat)
                return PlantEnum.WHEAT;
            if(block == Blocks.potatoes)
                return PlantEnum.POTATO;
            if(block == Blocks.carrots)
                return PlantEnum.POTATO;
            if(block == Blocks.melon_stem)
                return PlantEnum.MELON;
            if(block == Blocks.pumpkin_stem)
                return PlantEnum.PUMPKIN;
            if(block == ModBlocks.moonglow)
                return PlantEnum.MOONGLOW;
            if(block == ModBlocks.lotusBlock)
                return PlantEnum.LOTUS;
        }

        return null;
    }

    public static Block getPlantFromEnum(PlantEnum plantEnum)
    {
        if(plantEnum == PlantEnum.WHEAT)
            return Blocks.wheat;
        if(plantEnum == PlantEnum.POTATO)
            return Blocks.potatoes;
        if(plantEnum == PlantEnum.CARROT)
            return Blocks.carrots;
        if(plantEnum == PlantEnum.MELON)
            return Blocks.melon_stem;
        if(plantEnum == PlantEnum.BLOODWART)
            return ModBlocks.bloodwart;
        if(plantEnum == PlantEnum.PUMPKIN)
            return Blocks.pumpkin_stem;
        if(plantEnum == PlantEnum.MOONGLOW)
            return ModBlocks.moonglow;
        if(plantEnum == PlantEnum.LOTUS)
            return ModBlocks.lotusBlock;

        return null;
    }


    public boolean arePlantsValid(CeremonyRegistry ceremonyRegistry)
    {
        /*
        World world = this.worldObj;

        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;

        Block plant1 = world.getBlock(x + 3, y, z);
        Block plant2 = world.getBlock(x - 3, y, z);
        Block plant3 = world.getBlock(x, y, z + 3);
        Block plant4 = world.getBlock(x, y, z - 3);

        PlantEnum plantID1 = ceremonyRegistry.getPlant1();
        PlantEnum plantID2 = ceremonyRegistry.getPlant2();
        PlantEnum plantID3 = ceremonyRegistry.getPlant3();
        PlantEnum plantID4 = ceremonyRegistry.getPlant4();

        boolean possibility1 = plantID1 == getEnumFromPlant(plant1) && plantID2 == getEnumFromPlant(plant2) && plantID3 == getEnumFromPlant(plant3) && plantID4 == getEnumFromPlant(plant4);

        if(plant1 != null && plant2 != null && plant3 != null && plant4 != null && getEnumFromPlant(world.getBlock(x + 3, y, z)) != null && getEnumFromPlant(world.getBlock(x - 3, y, z)) != null && getEnumFromPlant(world.getBlock(x, y, z + 3)) != null && getEnumFromPlant(world.getBlock(x, y, z - 3)) != null)
        {
            if(possibility1)
            {
                return true;
            }
        }
        */

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
            for(int j = -yAxis; j <= yAxis; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    Block plantSelected = world.getBlock(xCoord + i, yCoord + j, zCoord + k);
                    if(plantSelected != null)
                    {

                        boolean isNotFlower = !plantSelected.getUnlocalizedName().contains("flower");
                        boolean isNotBush = !plantSelected.getUnlocalizedName().contains("bush");
                        boolean isNotBerry = !plantSelected.getUnlocalizedName().contains("berry");
                        boolean isNotKelp = !plantSelected.getUnlocalizedName().contains("kelp");

                        if(plantSelected instanceof IPlantable && isNotFlower && isNotBerry && isNotBush && isNotKelp)
                        {
                            if(world.getBlockMetadata(xCoord + i, yCoord + j, zCoord + k) >= 3)
                            {
                                if(plantEfficiency < 100)
                                {
                                    if(plantEfficiency + getPlantDrained(plantSelected) > 100)
                                    {
                                        world.setBlockMetadataWithNotify(xCoord + i, yCoord + j, zCoord + k, world.getBlockMetadata(xCoord + i, yCoord + j, zCoord + k) - 1, 2);
                                        plantEfficiency = 100;
                                    } else
                                    {
                                        plantEfficiency += getPlantDrained(plantSelected);
                                    }
                                }
                            }
                        }
                    }
                }
    }

    public int getEffiencyFromBlock(Block block)
    {
        //if(efficiency < 50)
        {
            if(block == ModBlocks.totemTorch)
                return 5;
            if(block == ModBlocks.totemSocket)
                return 3;
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
        nbtTagCompound.setInteger("dancingEfficiency", dancingEfficiency);
        nbtTagCompound.setIntArray("music", music);
        nbtTagCompound.setInteger("plantEfficiency", plantEfficiency);
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
        dancingEfficiency = nbtTagCompound.getInteger("dancingEfficiency");
        currentTime = nbtTagCompound.getInteger("currentTime");
        music = nbtTagCompound.getIntArray("music");
        plantEfficiency = nbtTagCompound.getInteger("plantEfficiency");
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
    public int[] getMusicArray()
    {
        return this.music;
    }

    @Override
    public int[] getMusicSelector()
    {
        return musicSelector;
    }
}
